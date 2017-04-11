from rest_framework import serializers
from models import RemittanceDetails


class RemittanceDetailsSerializer(serializers.ModelSerializer):
    class Meta:
        model = RemittanceDetails
        fields = ('name', 'address', 'amount', 'contact_no')

    def create(self, validated_data):
        return RemittanceDetails.objects.create(**validated_data)
