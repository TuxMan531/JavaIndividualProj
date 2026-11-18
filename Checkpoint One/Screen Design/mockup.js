// Sample data for the Checkpoint One screen design.
const films = [
  {year: '2021', summary: 'Paul Atreides travels to Arrakis as rival houses compete for control of its spice.'},
  {year: '1984', summary: 'David Lynch’s adaptation follows a young heir into a struggle over a desert planet.'}
];
const byId = id => document.getElementById(id);
const watchlist = new Map();
let signedIn = false;
let pendingFilm = null;
let query = 'Dune';

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
  watchlist.set(film.year, false);
  render();
  announce('Dune (' + film.year + ') saved to your watchlist.');
}
function card(film, inLibrary) {
  const item = byId('movie').content.firstElementChild.cloneNode(true);
  item.querySelector('.poster').classList.toggle('classic', film.year === '1984');
  item.querySelector('small').textContent = film.year;
  item.querySelector('.year').textContent = film.year;
  item.querySelector('.summary').textContent = film.summary;
  const controls = item.querySelector('.actions');
  if (inLibrary) {
    const watched = watchlist.get(film.year);
    controls.append(action(watched ? 'Watched ✓' : 'Mark watched', () => {
      watchlist.set(film.year, !watched);
      render();
      announce('Dune (' + film.year + ') marked ' + (watched ? 'unwatched.' : 'watched.'));
    }), action('Remove', () => {
      watchlist.delete(film.year);
      render();
      announce('Removed Dune (' + film.year + ').');
    }));
  } else {
    const saved = signedIn && watchlist.has(film.year);
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
  const found = films.filter(f => ('dune ' + f.year).includes(query.toLowerCase()));
  byId('result-count').textContent = found.length ? found.length + (found.length === 1 ? ' film' : ' films') + (query ? ' matching “' + query + '”' : '') : 'No films found. Try Dune or a release year.';
  byId('results').replaceChildren(...found.map(f => card(f, false)));
  const saved = films.filter(f => signedIn && watchlist.has(f.year));
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
  announce('');
  render();
};
window.addEventListener('hashchange', () => { announce(''); render(); });
render();
