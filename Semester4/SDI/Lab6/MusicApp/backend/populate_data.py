import os
from faker import Faker

def replace_quotes(text):
        return text.replace("'", "`")

if __name__ == '__main__':

        fake = Faker()
        fake.add_provider(replace_quotes)
        batch_size = 1000
        with open('music.sql', 'w') as file:

                sql = """TRUNCATE TABLE "Music_app_artist" RESTART IDENTITY CASCADE;\n \
                TRUNCATE TABLE "Music_app_album" RESTART IDENTITY CASCADE;\n \
                TRUNCATE TABLE "Music_app_song" RESTART IDENTITY CASCADE;\n \
                TRUNCATE TABLE "Music_app_performson" RESTART IDENTITY CASCADE;\n """
                file.write(sql + "\n")

                for i in range(0, 1000000, 1000):
                        artists = []
                        for j in range(i, i+1000):
                                artist_name = replace_quotes(fake.name())
                                real_name = replace_quotes(fake.name())
                                country = replace_quotes(fake.country())
                                email = replace_quotes(fake.email())
                                artists.append(f"('{artist_name}', '{real_name}', '{country}', '{email}')")
                        data = f"""INSERT INTO "Music_app_artist" (artist_name, real_name, country, email) VALUES {','.join(artists)};"""
                        file.write(data + "\n")

                print("Artists added")
                file.write("SELECT 'artists done!' as msg;\n")

                for i in range(0, 1000000, 1000):
                        albums = []
                        for j in range(i, i+1000):
                                album_title = replace_quotes(fake.text(max_nb_chars=25))
                                nr_of_tracks = fake.random_int(min=1, max=50)
                                label = replace_quotes(fake.text(max_nb_chars=10))
                                main_artist_id = fake.random_int(min=1, max=1000000)
                                year_of_release = fake.random_int(min=1900, max=2023)
                                albums.append(f"('{album_title}', '{nr_of_tracks}', '{label}', '{year_of_release}', '{main_artist_id}')")
                        data = f"""INSERT INTO "Music_app_album" (album_title, nr_of_tracks, label, year_of_release, main_artist_id) VALUES {','.join(albums)};"""
                        file.write(data + "\n")

                print("Albums added")
                file.write("SELECT 'albums done!' as msg;\n")

                for i in range(0, 1000000, 1000):
                        songs = []
                        for j in range(i, i+1000):
                                song_name = replace_quotes(fake.text(max_nb_chars=25))
                                composer = replace_quotes(fake.name())
                                genre = fake.random_element(elements=("pop", "electronic", "dance", "dnb", "soul", "reggae", "classical", "hip hop", "blues", "rock", "jazz"))
                                year_of_release = fake.random_int(min=1900, max=2023)
                                songs.append(f"('{song_name}', '{composer}', '{genre}', '{year_of_release}')")
                        data = f"""INSERT INTO "Music_app_song" (song_name, composer, genre, year_of_release) VALUES {','.join(songs)};"""
                        file.write(data + "\n")

                print("Songs added")
                file.write("SELECT 'songs done!' as msg;\n")

                nr = 10000000
                for i in range(10000):
                        if i % 1000 == 0:
                                print(f'{i*1000} done')
                        artist_id = fake.random_int(min=1, max=1000000)
                        performances = []
                        for j in range(1000):
                                song_id = fake.random_int(min=1, max=1000000)
                                nr_of_views = fake.random_int(min=0, max=70000000)
                                duration = fake.pystr_format(string_format='##:##')
                                performances.append(f"('{nr_of_views}', '{duration}', '{artist_id}', '{song_id}')")
                        data = f"""INSERT INTO "Music_app_performson" (nr_of_views, duration, artist_id, song_id) VALUES {','.join(performances)};"""
                        file.write(data + "\n")

                print("PerformsOn added")
                file.write("SELECT 'all done!' as msg;\n")
