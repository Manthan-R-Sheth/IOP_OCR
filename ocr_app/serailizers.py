from rest_framework import serializers
from models import RemittanceDetails


class RemittanceDetailsSerializer(serializers.ModelSerializer):
    class Meta:
        model = RemittanceDetails
        fields = ('name', 'card_number', 'address', 'amount', 'exp_date')

    def create(self, validated_data):
        return RemittanceDetails.objects.create(**validated_data)
