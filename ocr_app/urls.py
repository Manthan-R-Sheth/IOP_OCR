from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^details/', views.Details.as_view()),
    url(r'^extractFromImage/', views.Extract.as_view()),
]
