from django import forms
from django.contrib.auth.models import User
# from .models import Item,Comment,Recipe


class RegistrationForm(forms.ModelForm):
    password=forms.CharField(widget=forms.PasswordInput())
    confirm_password=forms.CharField(widget=forms.PasswordInput())
    class Meta:
        model = User
        fields = ('username','email','password')


