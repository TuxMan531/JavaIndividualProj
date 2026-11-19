# Weekly reflections

## Week of November 16, 2026

This week I narrowed Movie Hunter down to a simple flow for the design mock up, i want to be able to search for a movie, see the year with a short description and save it from one card
 i wrote five user stories and broke the work into checkpoints making the HTML and CSS mockup helped me decide what should belong on each card and I used placeholder posters so I could focus on how someone gets from the home page to their watchlist but they looked warped and odd and i don't think it matterd all that much for the design mockup

Time spent on Movie Hunter: 3h 


## Checkpoint 2, database and dao

What I got done:
The database for Movie Hunter is made and good, it has a user, and watchlist_entry table with a few sample movies in it
I also made the three entities and a generic dao based on the one from the week 5 video it can insert, get, update and delete 
I wrote 13 tests for it using watchlist entryes and they pass, and Log4J is set up so there's no printlns anywhere (almost forgot this) I also put my design mockup in the webapp folder and got it running on Tomcat just so there's something to look at

What challenged me:
The relationships took me the longest, a watchlist entry belongs to one user and one movie, I think we did something similar in adv java but i don't rmember it too well 
and the same user shouldn't be able to save the same movie twice but two different users should be able to save the same one

What problems I solved and what I used to solve them:
For the duplicate saves I put a unique key on the user and movie together in the table and tested that it gets rejected
I also used the Database class from the demos to reset the test data this time like the week 4 feedback said 
Tomcat wasn't installed on my mac so I installed Tomcat 9 to run the project. I used the week 5 generic dao video and my week 4 and 5 exercises, i got a diffrent macbook redcently so some small config things like this evreywhere
but i did make a backup so i didn't have to like re-setup evreything 

What's next:
Checkpoint 3, cognito sign in, a jsp that shows the watchlist from the database, and getting it deployed on aws

Time spent on Movie Hunter: 2 maybe 3ish some hours with me getting distracted included
