import random
import string
from django.http import JsonResponse
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt

def generate_password(length, numbers=True, uppercase=False, lowercase=False, special=False):
    charset = ''
    if numbers:
        charset += string.digits
    if uppercase:
        charset += string.ascii_uppercase
    if lowercase:
        charset += string.ascii_lowercase
    if special:
        charset += string.punctuation

    if not charset:
        raise ValueError("Please select at least one option.")

    password = ''.join(random.choice(charset) for _ in range(length))
    return password

@csrf_exempt
def password_generator(request):
    if request.method == 'POST':
        try:
            length = int(request.POST.get('length', 8))
            if length < 8 or length > 35:
                return JsonResponse({"error": "Password length must be between 8 and 35."}, status=400)

            numbers = request.POST.get('numbers') == 'on'
            uppercase = request.POST.get('uppercase') == 'on'
            lowercase = request.POST.get('lowercase') == 'on'
            special = request.POST.get('special') == 'on'

            password = generate_password(length, numbers, uppercase, lowercase, special)
            return JsonResponse({"password": password})
        except (ValueError, TypeError) as e:
            return JsonResponse({"error": str(e)}, status=400)
    else:
        return render(request, 'password_form.html')

# In your Django project, add this view to urls.py
# urlpatterns = [
#     path('generate-password/', password_generator, name='password_generator'),
# ]
