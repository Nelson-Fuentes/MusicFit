from rest_framework import serializers
from django.contrib.auth.models import User


class UserRegistrationSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = (
            'username',
            'first_name',
            'last_name',
            'email',
            'password'
        )

    def create(self, validated_data):
        user = super(UserRegistrationSerializer, self).create(validated_data)
        user.set_password(validated_data['password'])
        user.save()
        return user