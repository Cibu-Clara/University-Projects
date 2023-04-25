from django.contrib import admin

# Register your models here.
from backend.Music.models import Artist, Song, Album, PerformsOn

admin.site.register(Artist)
admin.site.register(Song)
admin.site.register(Album)
admin.site.register(PerformsOn)