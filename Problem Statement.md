# Problem statement

A friend recommends *Dune*. Later the viewer remembers the title but can't remember whether the recommendation was for the 1984 film or the 2021 one.
They want to see the year, recognize the poster, read a short description, and save the right film before they forget. For me, that quick lookup on IMDb can feel slow and dated, the few details I came for can take several cliks / layers of interference to get to. sevel more than needed at least

**Movie Hunter** will put those details together on one search result card. A viewer can compare films with the same title and save one directly to a watchlist
When they return they can see what they meant to watch, mark a film watched, or remove it -- this will also need a user to have an account

The first version will can use a movie metadata service for discovery and a local application database for each user's choices
Its success test is: a viewer searching for *Dune* can identify and save the intended release from the results, then find it again on their next visit with minimal interference, indeally very easily
