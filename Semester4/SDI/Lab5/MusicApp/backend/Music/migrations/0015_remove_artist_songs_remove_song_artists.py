# Generated by Django 4.1.7 on 2023-04-21 10:52

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Music', '0014_rename_artist_id_performson_artist_and_more'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='artist',
            name='songs',
        ),
        migrations.RemoveField(
            model_name='song',
            name='artists',
        ),
    ]