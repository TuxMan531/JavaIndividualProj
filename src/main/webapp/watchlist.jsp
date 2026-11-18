<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> <%-- or else the dot between the year and the name shows up as weird characters --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Movie Hunter</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
  <header class="wrap">
    <a class="brand" href="index.html">Movie Hunter</a>
    <nav aria-label="Main navigation">
      <a href="index.html">Discover</a>
      <a href="watchlist">Watchlist</a>
    </nav>
  </header>
  <main class="wrap">
    <h1>Watchlist</h1>
    <%-- same cards as the mockup but these ones come from the database, ViewWatchlist sends the entries --%>
    <p>${entries.size()} saved movies</p>
    <div class="cards">
      <c:forEach var="entry" items="${entries}">
        <article>
          <div class="poster" aria-hidden="true">Example</div>
          <div class="body">
            <h3><c:out value="${entry.movie.title}"/></h3>
            <p>${entry.movie.releaseYear} · saved by <c:out value="${entry.user.username}"/></p>
            <p>${entry.watched ? 'Watched' : 'Not watched yet'}</p>
          </div>
        </article>
      </c:forEach>
    </div>
  </main>
</body>
</html>
