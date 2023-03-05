package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;
   // private Object exception;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        User newUser=new User(name,mobile);
        users.add(newUser);
        return newUser;
    }

    public Artist createArtist(String name) {
        Artist newArtist=new Artist(name);
        artists.add(newArtist);
        artistAlbumMap.put(newArtist,new ArrayList<Album>());
        return newArtist;
    }

    public Album createAlbum(String title, String artistName) {
        if(!artists.contains(artistName)){
            Artist a=new Artist(artistName);
            artists.add(a);
            artistAlbumMap.put(a,new ArrayList<Album>());
        }
        Album newAlbum=new Album(title);
        albums.add(newAlbum);
        albumSongMap.put(newAlbum,new ArrayList<Song>());
        return newAlbum;
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
        Song s=new Song(title,length);
        songs.add(s);
        if(albums.contains(albumName)){
            albumSongMap.get(albumName).add(s);
        }else throw new Exception("Album does not exist");


        return s;

    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        Playlist p=new Playlist(title);
        ArrayList<Song> s=new ArrayList<>();
        ArrayList<User> list=new ArrayList<>();
        for(int i=0;i<songs.size();i++){
            if(songs.get(i).getLength()==length)
                s.add(songs.get(i));
        }
        playlistSongMap.put(p,s);
        int flag=0;
        for(int i=0;i<users.size();i++){
            if(users.get(i).getMobile()==mobile) {
                flag++;
                list.add(users.get(i));
            }
        }

        if(flag==0)
            throw new Exception("User does not exist");
        else playlistListenerMap.put(p,list);
        return p;
    }
     //HashMap<Playlist,List<Song>> hm=new HashMap<>();
    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        Playlist p=new Playlist(title);
        ArrayList<Song> list=new ArrayList<>();
        for(int i=0;i<songs.size();i++){
            if(songTitles.contains(songs.get(i).getTitle()))
             list.add(songs.get(i));
        }
        playlistSongMap.put(p,list);
        ArrayList<User> l=new ArrayList<>();
        int flag=0;
        for(int i=0;i<users.size();i++){
            if(users.get(i).getMobile()==mobile) {
                flag++;
                l.add(users.get(i));
            }
        }

        if(flag==0)
            throw new Exception("User does not exist");
        else playlistListenerMap.put(p,l);
        return p;

    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        if(playlistSongMap.containsKey(playlistTitle))

    }

    public Song likeSong(String mobile, String songTitle) throws Exception {

    }

    public String mostPopularArtist() {
    }

    public String mostPopularSong() {
    }
}
