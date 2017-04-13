from __future__ import unicode_literals

from django.db import models


# Create your models here.
class RemittanceDetails(models.Model):
    exp_date = models.IntegerField(default='')
    card_number = models.IntegerField(default='')
    address = models.CharField(max_length=100, default='')
    amount = models.FloatField(default='')
    name = models.CharField()

    REQUIRED_FIELDS = ['name', 'card_number', 'address', 'amount', 'exp_date']

    def __unicode__(self):
        return self.name
