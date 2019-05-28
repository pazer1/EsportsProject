package com.dipdoo.esportsproject.Global;

import java.io.Serializable;
import java.util.List;

public class Match implements Serializable {

    /**
     * begin_at : null
     * draw : true
     * games : [{"begin_at":null,"finished":null,"id":1,"length":null,"match_id":1,"position":1,"winner":{"id":null,"type":null},"winner_type":null}]
     * id : 1
     * league : {"id":1,"image_url":null,"live_supported":true,"modified_at":null,"name":"string","slug":"string","url":null}
     * league_id : 1
     * live : {"opens_at":null,"supported":true,"url":null}
     * match_type : null
     * modified_at : null
     * name : string
     * number_of_games : null
     * opponents : [{"opponent":{"first_name":null,"hometown":null,"id":1,"image_url":null,"last_name":null,"name":"string","role":null,"slug":null},"type":"Player"}]
     * results : [{"score":0,"team_id":1}]
     * serie : {"begin_at":null,"description":null,"end_at":null,"full_name":"string","id":1,"league_id":1,"modified_at":null,"name":null,"prizepool":null,"season":null,"slug":"string","winner_id":null,"winner_type":null,"year":1912}
     * serie_id : 1
     * slug : null
     * status : not_started
     * tournament : {"begin_at":null,"end_at":null,"id":1,"league_id":1,"modified_at":null,"name":"string","serie_id":1,"slug":"string","winner_id":null,"winner_type":null}
     * tournament_id : 1
     * videogame : {"id":1,"name":"LoL","slug":"league-of-legends"}
     * videogame_version : null
     * winner : null
     * winner_id : null
     */

    private String begin_at;
    private boolean draw;
    private int id;
    private LeagueBean league;
    private int league_id;
    private LiveBean live;
    private Object match_type;
    private Object modified_at;
    private String name;
    private Object number_of_games;
    private SerieBean serie;
    private int serie_id;
    private Object slug;
    private String status;
    private TournamentBean tournament;
    private int tournament_id;
    private VideogameBean videogame;
    private Object videogame_version;
    private Object winner;
    private Object winner_id;
    private List<GamesBean> games;
    private List<OpponentsBean> opponents;
    private List<ResultsBean> results;

    public String getBegin_at() {
        return begin_at;
    }

    public void setBegin_at(String begin_at) {
        this.begin_at = begin_at;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LeagueBean getLeague() {
        return league;
    }

    public void setLeague(LeagueBean league) {
        this.league = league;
    }

    public int getLeague_id() {
        return league_id;
    }

    public void setLeague_id(int league_id) {
        this.league_id = league_id;
    }

    public LiveBean getLive() {
        return live;
    }

    public void setLive(LiveBean live) {
        this.live = live;
    }

    public Object getMatch_type() {
        return match_type;
    }

    public void setMatch_type(Object match_type) {
        this.match_type = match_type;
    }

    public Object getModified_at() {
        return modified_at;
    }

    public void setModified_at(Object modified_at) {
        this.modified_at = modified_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getNumber_of_games() {
        return number_of_games;
    }

    public void setNumber_of_games(Object number_of_games) {
        this.number_of_games = number_of_games;
    }

    public SerieBean getSerie() {
        return serie;
    }

    public void setSerie(SerieBean serie) {
        this.serie = serie;
    }

    public int getSerie_id() {
        return serie_id;
    }

    public void setSerie_id(int serie_id) {
        this.serie_id = serie_id;
    }

    public Object getSlug() {
        return slug;
    }

    public void setSlug(Object slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TournamentBean getTournament() {
        return tournament;
    }

    public void setTournament(TournamentBean tournament) {
        this.tournament = tournament;
    }

    public int getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    public VideogameBean getVideogame() {
        return videogame;
    }

    public void setVideogame(VideogameBean videogame) {
        this.videogame = videogame;
    }

    public Object getVideogame_version() {
        return videogame_version;
    }

    public void setVideogame_version(Object videogame_version) {
        this.videogame_version = videogame_version;
    }

    public Object getWinner() {
        return winner;
    }

    public void setWinner(Object winner) {
        this.winner = winner;
    }

    public Object getWinner_id() {
        return winner_id;
    }

    public void setWinner_id(Object winner_id) {
        this.winner_id = winner_id;
    }

    public List<GamesBean> getGames() {
        return games;
    }

    public void setGames(List<GamesBean> games) {
        this.games = games;
    }

    public List<OpponentsBean> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<OpponentsBean> opponents) {
        this.opponents = opponents;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class LeagueBean implements Serializable{
        /**
         * id : 1
         * image_url : null
         * live_supported : true
         * modified_at : null
         * name : string
         * slug : string
         * url : null
         */

        private int id;
        private Object image_url;
        private boolean live_supported;
        private Object modified_at;
        private String name;
        private String slug;
        private Object url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getImage_url() {
            return image_url;
        }

        public void setImage_url(Object image_url) {
            this.image_url = image_url;
        }

        public boolean isLive_supported() {
            return live_supported;
        }

        public void setLive_supported(boolean live_supported) {
            this.live_supported = live_supported;
        }

        public Object getModified_at() {
            return modified_at;
        }

        public void setModified_at(Object modified_at) {
            this.modified_at = modified_at;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
        }
    }

    public static class LiveBean implements Serializable{
        /**
         * opens_at : null
         * supported : true
         * url : null
         */

        private Object opens_at;
        private boolean supported;
        private Object url;

        public Object getOpens_at() {
            return opens_at;
        }

        public void setOpens_at(Object opens_at) {
            this.opens_at = opens_at;
        }

        public boolean isSupported() {
            return supported;
        }

        public void setSupported(boolean supported) {
            this.supported = supported;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
        }
    }

    public static class SerieBean implements Serializable {
        /**
         * begin_at : null
         * description : null
         * end_at : null
         * full_name : string
         * id : 1
         * league_id : 1
         * modified_at : null
         * name : null
         * prizepool : null
         * season : null
         * slug : string
         * winner_id : null
         * winner_type : null
         * year : 1912
         */

        private Object begin_at;
        private Object description;
        private Object end_at;
        private String full_name;
        private int id;
        private int league_id;
        private Object modified_at;
        private Object name;
        private Object prizepool;
        private Object season;
        private String slug;
        private Object winner_id;
        private Object winner_type;
        private int year;

        public Object getBegin_at() {
            return begin_at;
        }

        public void setBegin_at(Object begin_at) {
            this.begin_at = begin_at;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public Object getEnd_at() {
            return end_at;
        }

        public void setEnd_at(Object end_at) {
            this.end_at = end_at;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLeague_id() {
            return league_id;
        }

        public void setLeague_id(int league_id) {
            this.league_id = league_id;
        }

        public Object getModified_at() {
            return modified_at;
        }

        public void setModified_at(Object modified_at) {
            this.modified_at = modified_at;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getPrizepool() {
            return prizepool;
        }

        public void setPrizepool(Object prizepool) {
            this.prizepool = prizepool;
        }

        public Object getSeason() {
            return season;
        }

        public void setSeason(Object season) {
            this.season = season;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public Object getWinner_id() {
            return winner_id;
        }

        public void setWinner_id(Object winner_id) {
            this.winner_id = winner_id;
        }

        public Object getWinner_type() {
            return winner_type;
        }

        public void setWinner_type(Object winner_type) {
            this.winner_type = winner_type;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public static class TournamentBean implements Serializable{
        /**
         * begin_at : null
         * end_at : null
         * id : 1
         * league_id : 1
         * modified_at : null
         * name : string
         * serie_id : 1
         * slug : string
         * winner_id : null
         * winner_type : null
         */

        private Object begin_at;
        private Object end_at;
        private int id;
        private int league_id;
        private Object modified_at;
        private String name;
        private int serie_id;
        private String slug;
        private Object winner_id;
        private Object winner_type;

        public Object getBegin_at() {
            return begin_at;
        }

        public void setBegin_at(Object begin_at) {
            this.begin_at = begin_at;
        }

        public Object getEnd_at() {
            return end_at;
        }

        public void setEnd_at(Object end_at) {
            this.end_at = end_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLeague_id() {
            return league_id;
        }

        public void setLeague_id(int league_id) {
            this.league_id = league_id;
        }

        public Object getModified_at() {
            return modified_at;
        }

        public void setModified_at(Object modified_at) {
            this.modified_at = modified_at;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSerie_id() {
            return serie_id;
        }

        public void setSerie_id(int serie_id) {
            this.serie_id = serie_id;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public Object getWinner_id() {
            return winner_id;
        }

        public void setWinner_id(Object winner_id) {
            this.winner_id = winner_id;
        }

        public Object getWinner_type() {
            return winner_type;
        }

        public void setWinner_type(Object winner_type) {
            this.winner_type = winner_type;
        }
    }

    public static class VideogameBean implements Serializable{
        /**
         * id : 1
         * name : LoL
         * slug : league-of-legends
         */

        private int id;
        private String name;
        private String slug;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }
    }

    public static class GamesBean implements Serializable {
        /**
         * begin_at : null
         * finished : null
         * id : 1
         * length : null
         * match_id : 1
         * position : 1
         * winner : {"id":null,"type":null}
         * winner_type : null
         */

        private Object begin_at;
        private Object finished;
        private int id;
        private Object length;
        private int match_id;
        private int position;
        private WinnerBean winner;
        private Object winner_type;

        public Object getBegin_at() {
            return begin_at;
        }

        public void setBegin_at(Object begin_at) {
            this.begin_at = begin_at;
        }

        public Object getFinished() {
            return finished;
        }

        public void setFinished(Object finished) {
            this.finished = finished;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getLength() {
            return length;
        }

        public void setLength(Object length) {
            this.length = length;
        }

        public int getMatch_id() {
            return match_id;
        }

        public void setMatch_id(int match_id) {
            this.match_id = match_id;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public WinnerBean getWinner() {
            return winner;
        }

        public void setWinner(WinnerBean winner) {
            this.winner = winner;
        }

        public Object getWinner_type() {
            return winner_type;
        }

        public void setWinner_type(Object winner_type) {
            this.winner_type = winner_type;
        }

        public static class WinnerBean implements Serializable{
            /**
             * id : null
             * type : null
             */

            private Object id;
            private Object type;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }
        }
    }

    public static class OpponentsBean implements Serializable{
        /**
         * opponent : {"first_name":null,"hometown":null,"id":1,"image_url":null,"last_name":null,"name":"string","role":null,"slug":null}
         * type : Player
         */

        private OpponentBean opponent;
        private String type;

        public OpponentBean getOpponent() {
            return opponent;
        }

        public void setOpponent(OpponentBean opponent) {
            this.opponent = opponent;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class OpponentBean implements Serializable{
            /**
             * first_name : null
             * hometown : null
             * id : 1
             * image_url : null
             * last_name : null
             * name : string
             * role : null
             * slug : null
             */

            private Object first_name;
            private Object hometown;
            private int id;
            private Object image_url;
            private Object last_name;
            private String name;
            private Object role;
            private Object slug;

            public Object getFirst_name() {
                return first_name;
            }

            public void setFirst_name(Object first_name) {
                this.first_name = first_name;
            }

            public Object getHometown() {
                return hometown;
            }

            public void setHometown(Object hometown) {
                this.hometown = hometown;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getImage_url() {
                return image_url;
            }

            public void setImage_url(Object image_url) {
                this.image_url = image_url;
            }

            public Object getLast_name() {
                return last_name;
            }

            public void setLast_name(Object last_name) {
                this.last_name = last_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getRole() {
                return role;
            }

            public void setRole(Object role) {
                this.role = role;
            }

            public Object getSlug() {
                return slug;
            }

            public void setSlug(Object slug) {
                this.slug = slug;
            }
        }
    }

    public static class ResultsBean implements Serializable{
        /**
         * score : 0
         * team_id : 1
         */

        private int score;
        private int team_id;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }
    }
}

