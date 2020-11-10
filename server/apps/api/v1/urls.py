from django.contrib import admin
from django.urls import path, include

urlpatterns = [
    path('auth/', include(("apps.api.v1.auth.urls", "auth"))),
    path('user/', include(("apps.api.v1.user.urls", "user")))
]