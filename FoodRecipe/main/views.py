from django.shortcuts import render, redirect
from django.http import HttpResponse,request
from .forms import RegistrationForm
from  django.contrib.auth.models import User,auth
from  django.contrib.auth import login,logout, authenticate
from django.contrib.auth.forms import AuthenticationForm



def home(request):
    return render(request,'main/index.html')


# Create your views here.
def register(request):
    context={ }
    if request.method == "POST":
        form = RegistrationForm(request.POST)
        if form.is_valid():
           form.save()
           return redirect('home')
        else:
            print('sdfsdfs')
    else:
        form = RegistrationForm
        context['form'] = form
    return render(request,'main/register.html',context)


def login(request):
    
    if request.method == "POST":
        form = AuthenticationForm(request, data = request.POST)
        if form.is_valid():
            print("sdfasdfasdfsdf")
            username = form.cleaned_data.get("username")
            password = form.cleaned_data.get("password")
            user = auth.authenticate(username = username, password = password)
            if user is not None:
                auth.login(request, user)
                # messages.success(request, f'you have logged as {{ username }}')
                return redirect('main:Homepage')
            else:
                print("error")
    else:
        form = AuthenticationForm()
    return render(request, "main/login.html", context = {"form" : form})	
    # context ={}
    # if request.method == 'POST':
    #     form = AuthenticationForm(request, data = request.POST)
    #     username=request.POST['username']
    #     password = request.POST['password']
    #     print("sadfasdfasdf",username)
    #     user= auth.authenticate(username=username,password=password)
    #     if user is not None:
    #        auth.login(request,user)
    #        print("login successfull")
    #        return redirect('register')
        
    #     else:
    #         context["error"]="invalid password and username"
    #         return render(request,'main/login.html',context)
    # else:
    #     return render(request,'main/login.html',context)
    
