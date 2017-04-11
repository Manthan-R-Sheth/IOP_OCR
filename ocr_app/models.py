from __future__ import unicode_literals

from django.db import models


# Create your models here.
class RemittanceDetails(models.Model):
    contact_no = models.CharField(max_length=10, default='')
    name = models.EmailField(db_index=True)
    address = models.CharField(max_length=100, default='')
    amount = models.FloatField(default='')

    REQUIRED_FIELDS = ['name', 'address', 'amount', 'contact_no']
    USERNAME_FIELD = 'contact_no'

    def __unicode__(self):
        return self.name
