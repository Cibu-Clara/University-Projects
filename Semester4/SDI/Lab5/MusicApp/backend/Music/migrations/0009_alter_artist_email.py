# Generated by Django 4.1.7 on 2023-04-09 20:30

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Music', '0008_remove_artist_cnp'),
    ]

    operations = [
        migrations.AlterField(
            model_name='artist',
            name='email',
            field=models.EmailField(max_length=100),
        ),
    ]
