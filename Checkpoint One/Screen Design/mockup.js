// Ratings are snapshots from IMDb. Each badge links to its source.
const films = [
  {id: 'tt0816692', title: 'Interstellar', year: '2014', genre: 'Sci-Fi', rating: '8.7', featured: true, summary: 'A pilot leaves his family on a failing Earth to search for a new home beyond a wormhole.'},
  {id: 'tt0133093', title: 'The Matrix', year: '1999', genre: 'Sci-Fi', rating: '8.7', featured: true, summary: 'A hacker follows a stranger out of the world he knows and learns who built it.'},
  {id: 'tt0468569', title: 'The Dark Knight', year: '2008', genre: 'Crime', rating: '9.1', featured: true, summary: 'Batman and Gotham’s new district attorney face a criminal who wants the city to turn on its heroes.'},
  {id: 'tt0111161', title: 'The Shawshank Redemption', year: '1994', genre: 'Drama', rating: '9.3', featured: true, summary: 'Inside Shawshank prison, a banker and a longtime inmate build a friendship that spans decades.'},
  {id: 'tt1160419', title: 'Dune', year: '2021', genre: 'Sci-Fi', rating: '8.0', summary: 'Paul Atreides travels to Arrakis as rival houses compete for control of its spice.'},
  {id: 'tt0087182', title: 'Dune', year: '1984', genre: 'Sci-Fi', rating: '6.2', summary: 'David Lynch’s adaptation follows a young heir into a struggle over a desert planet.'}
];
const byId = id => document.getElementById(id);
const watchlist = new Map();
let signedIn = false;
let pendingFilm = null;
let query = '';

function action(text, callback) {
  const button = document.createElement('button');
  button.textContent = text;
  button.addEventListener('click', callback);
  return button;
}
function announce(text) { byId('notice').textContent = text; }
function save(film) {
  if (!signedIn) {
    pendingFilm = film;
    byId('login').showModal();
    return;
  }
  watchlist.set(film.id, false);
  render();
  announce(film.title + ' (' + film.year + ') saved to your watchlist.');
}
function card(film, inLibrary) {
  const item = byId('movie').content.firstElementChild.cloneNode(true);
  item.querySelector('img').src = 'art/' + film.id + '.jpg';
  item.querySelector('h2').textContent = film.title;
  item.querySelector('.metadata').textContent = film.year + ' · ' + film.genre;
  const rating = item.querySelector('.rating');
  rating.href = 'https://www.imdb.com/title/' + film.id + '/ratings/';
  rating.textContent = film.rating + '/10';
  rating.setAttribute('aria-label', film.title + ' (' + film.year + '), IMDb rating ' + film.rating + ' out of 10');
  item.querySelector('.summary').textContent = film.summary;
  const controls = item.querySelector('.actions');
  if (inLibrary) {
    const watched = watchlist.get(film.id);
    controls.append(action(watched ? 'Watched ✓' : 'Mark watched', () => {
      watchlist.set(film.id, !watched);
      render();
      announce(film.title + ' (' + film.year + ') marked ' + (watched ? 'unwatched.' : 'watched.'));
    }), action('Remove', () => {
      watchlist.delete(film.id);
      render();
      announce('Removed ' + film.title + ' (' + film.year + ').');
    }));
  } else {
    const saved = signedIn && watchlist.has(film.id);
    const button = action(saved ? 'Saved ✓' : '+ Save to watchlist', () => save(film));
    button.disabled = saved;
    controls.append(button);
  }
  return item;
}
function render() {
  const inLibrary = location.hash === '#watchlist';
  byId('library').hidden = !inLibrary;
  byId('discover').hidden = inLibrary;
  byId('account').textContent = signedIn ? 'Sign out' : 'Sign in';
  byId('search-link').removeAttribute('aria-current');
  byId('list-link').removeAttribute('aria-current');
  byId(inLibrary ? 'list-link' : 'search-link').setAttribute('aria-current', 'page');
  const found = query
    ? films.filter(film => (film.title + ' ' + film.year).toLowerCase().includes(query.toLowerCase()))
    : films.filter(film => film.featured);
  const count = byId('result-count');
  if (!query) {
    const chart = document.createElement('a');
    chart.href = 'https://www.imdb.com/chart/top/';
    chart.textContent = 'IMDb Top 250';
    count.replaceChildren('Four picks from the ', chart);
  } else {
    count.textContent = found.length
      ? found.length + (found.length === 1 ? ' film' : ' films') + ' matching “' + query + '”'
      : 'No films found. Try Dune or a release year.';
  }
  byId('results').replaceChildren(...found.map(f => card(f, false)));
  const saved = films.filter(f => signedIn && watchlist.has(f.id));
  byId('list-count').textContent = !signedIn ? 'Sign in to see your saved films.' : saved.length ? saved.length + (saved.length === 1 ? ' saved film' : ' saved films') : 'Your watchlist is empty. Find a film in Discover to get started.';
  byId('saved').replaceChildren(...saved.map(f => card(f, true)));
}
byId('account').onclick = () => {
  if (signedIn) { signedIn = false; render(); announce('Signed out.'); }
  else { pendingFilm = null; byId('login').showModal(); }
};
byId('continue').onclick = () => {
  signedIn = true;
  byId('login').close();
  if (pendingFilm) save(pendingFilm);
  else { render(); announce('Signed in as demo viewer.'); }
  pendingFilm = null;
};
byId('search-form').onsubmit = event => {
  event.preventDefault();
  query = byId('query').value.trim();
  location.hash = '#search';
  announce('');
  render();
};
window.addEventListener('hashchange', () => { announce(''); render(); });
render();
