# Generated by Django 4.1.7 on 2023-03-06 14:43

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Song', '0002_rename_producer_song_label'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='song',
            name='id_song',
        ),
    ]
