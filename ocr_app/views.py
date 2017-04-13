from django.http import HttpResponseRedirect
from django.shortcuts import render, render_to_response
from django.template import RequestContext
from rest_framework import generics
from rest_framework import mixins
from rest_framework.response import Response

from models import RemittanceDetails
from serailizers import RemittanceDetailsSerializer


class Details(mixins.CreateModelMixin, generics.GenericAPIView):
    queryset = RemittanceDetails.objects.all()
    serializer_class = RemittanceDetailsSerializer
    pagination_class = None

    def post(self, request, *args, **kwargs):
        try:
            details = self.create(request, *args, **kwargs)
        except Exception as e:
            print type(e), e.message
            print request.data
            return Response({'detail': "Required fields not obtained"})
        return Response({"detail": "Data Added"})

