from rest_framework.authtoken import views
from django.urls import path, include

urlpatterns = [
    path('', views.obtain_auth_token, name="login")
]