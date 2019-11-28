import React, { Component } from 'react'
import { API_URL, genres, userName } from './../constants'

export default class Cinema extends Component {
  constructor() {
    super();

    this.state = {
      films: [],
      tickets: [],
      genreFilter: genres[0],
      bookedId: undefined,
      isTicketTab: false
    }
  }

  componentDidMount() {
    fetch(`${API_URL}films/`)
      .then(response => response.json())
      .then(films => this.setState({ films: [...films.map(film => ({ ...film, isOpened: false }))] }))
      .catch(console.error)

    this.fetchTickets();
  }

  fetchTickets = () => {
    fetch(`${API_URL}tickets/`)
      .then(response => response.json())
      .then(tickets => this.setState({ tickets: tickets.filter(({ client }) => client && client.name === userName).reverse() }))
      .catch(console.error)
  }

  bookTicket = async (filmName) => {
    const endpoint = `${API_URL}cinema/${userName}/${filmName}`;
    try {
      const response = await fetch(endpoint, { method: 'POST' });
      const result = await response.text();

      this.fetchTickets();
      if (result) alert('success')
    } catch (err) {
      console.error(err)
    } finally {
      this.setState({ bookedId: undefined })
    }
  }

  renderModal = () => {
    const { name, premiereDate, genre } = this.state.films.find(({ id }) => id === this.state.bookedId)
    return (
      <div className="bg-gray-200 flex items-center justify-center h-screen">
        <div className="flex items-center justify-center border-solid border-4 border-gray-600 p-8">
          <div
            className="modal-close absolute top-0 right-0 cursor-pointer flex flex-col items-center mt-4 mr-4 text-white text-sm z-50"
            onClick={() => this.setState({ bookedId: undefined })}
          >
            <svg className="fill-current text-white" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18">
              <path d="M14.53 4.53l-1.06-1.06L9 7.94 4.53 3.47 3.47 4.53 7.94 9l-4.47 4.47 1.06 1.06L9 10.06l4.47 4.47 1.06-1.06L10.06 9z"></path>
            </svg>
            <span className="text-sm">(Esc)</span>
          </div>
          <div>
            <div>
              {name}
            </div>
            <div>
              {premiereDate.slice(0, 10)}
            </div>
            <div>
              {genre}
            </div>
            <button onClick={() => this.bookTicket(name)} className="float-right bg-green-700">
              confirm
          </button>
          </div>
        </div>
      </div>
    );
  }

  toggleDetail = toggleId => {
    const films = this.state.films.map(film => film.id === toggleId ? { ...film, isOpened: !film.isOpened } : film)
    this.setState({ films })
  }

  renderFilms = () => {
    return this.state.films
      .filter(film => this.state.genreFilter !== 'all' ? film.genre === this.state.genreFilter : true)
      .map(({ id, name, premiereDate, genre, isOpened }) => {
        return (
          <div key={id} className="m-6 border-solid border-4 border-gray-600 mr-64">
            <div onClick={() => this.toggleDetail(id)}>
              <div>{name}</div>
            </div>
            {
              isOpened && (
                <div>
                  <div>
                    {premiereDate.slice(0, 10)}
                  </div>
                  <div>
                    {genre}
                  </div>
                </div>
              )
            }
            <button
              className="bg-gray-400"
              onClick={() => this.setState({ bookedId: id })}
            >
              buy
            </button>
          </div>
        )
      })
  }

  renderTickets = () => (
    <>
      <div>Tickets</div>
      {
        this.state.tickets.map(({ id, film: { name, premiereDate } }) => (
          <div key={id} className="m-6 border-solid border-4 border-gray-600 mr-64">
            <div>{name}</div>
            <div>{premiereDate.slice(0, 10)}</div>
          </div>
        ))
      }
    </>
  )

  render() {
    return this.state.bookedId ? this.renderModal() : (
      <div>
        <button
          onClick={() => this.setState({ isTicketTab: false })}
          className={`m-4 bg-grey-600 ${!this.state.isTicketTab ? 'opacity-50' : ''}`}
          disabled={!this.state.isTicketTab}
        >
          Book some film tickets
        </button>
        <button
          onClick={() => this.setState({ isTicketTab: true }) }
          className={`m-4 bg-grey-600 ${this.state.isTicketTab ? 'opacity-50' : ''}`}
          disabled={this.state.isTicketTab}
        >
          Watch my tickets
        </button>
        {
          this.state.isTicketTab ? this.renderTickets() : (
            <>
              <div className="flex">
                <div className="mr-8">Genres</div>
                <select
                  className="max-w-sm block appearance-none w-full bg-gray-200 border border-gray-200 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                  onChange={e => this.setState({ genreFilter: e.target.value })}
                  value={this.state.genreFilter}
                >
                  {genres.map(genre => <option key={genre}>{genre}</option>)}
                </select>
              </div>
              <div>
                {this.renderFilms()}
              </div>
            </>
          )
        }
      </div>
    )
  }
}
