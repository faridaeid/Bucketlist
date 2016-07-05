package edu.aucegypt.bucketlist;

/**
 * Created by Farida on 05-Jul-16.
 */
public class Post {

        private String task, personID, image, date, category;
        private int likes;
        private boolean completed;

        public Post(String task, String personID, String category,
                    String image, String date)
        {
            this.task = task;
            this.category = category;
            this.personID = personID;
            this.image = image;
            this.date = date;

            // defaults: uncompleted and 0 likes
            likes = 0;
            completed = false;
        }


        public String getTask() {return task;}
        public String getPersonID(){return personID;}
        public String getImage(){return image;}
        public String getDate(){return date;}
        public String getCategory() {return category;};
        public boolean getCompleted() {return completed;};
        public int getLikes(){return likes;}
}


