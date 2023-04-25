# Generated by Django 4.1.7 on 2023-04-09 20:44

import django.core.validators
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Music', '0010_alter_song_year_of_release'),
    ]

    operations = [
        migrations.AlterField(
            model_name='song',
            name='year_of_release',
            field=models.PositiveSmallIntegerField(validators=[django.core.validators.MinValueValidator(1800), django.core.validators.MaxValueValidator(2023)]),
        ),
    ]
