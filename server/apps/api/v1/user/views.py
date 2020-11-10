from django.contrib.auth.models import User
from rest_framework import generics, status
from rest_framework.response import Response

from apps.user.serializers import UserRegistrationSerializer


class UserCreateAPIView(generics.CreateAPIView):
    queryset = User.objects.all()
    serializer_class = UserRegistrationSerializer

    def post(self, request, *args, **kwargs):
        super().post(request, args, kwargs)
        return Response(status=status.HTTP_204_NO_CONTENT)