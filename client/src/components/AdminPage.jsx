import React, { Component } from 'react';
import { genres, API_URL } from './../constants';
import DatePicker from 'react-datepicker'

import 'react-datepicker/dist/react-datepicker.css';

const initialFilmState = {
  premiereDate: null,
  name: '',
  genre: ''
}

export default class AdminPage extends Component {
  constructor() {
    super();

    this.state = {
      films: [],
      currentFilm: { ...initialFilmState },
      filmIdToEdit: undefined
    }
  }

  componentDidMount() {
    this.fetchFilms();
  }

  fetchFilms = () => {
    fetch(`${API_URL}films/`)
      .then(response => response.json())
      .then(films => this.setState({ films }))
      .catch(console.error)
  }

  resetCurrentFilm = () => {
    this.setState({ currentFilm: { ...initialFilmState } })
  }

  setFilmIdToEdit = idToEdit => {
    const film = this.state.films.find(({ id }) => id === idToEdit);
    this.setState({ filmIdToEdit: idToEdit, currentFilm: { ...film, premiereDate: new Date(film.premiereDate) } })
  }

  deleteById = async (id) => {
    try {
      const response = await fetch(`${API_URL}films/${id}`, { method: 'DELETE' });
      if (response.status === 200) alert('success')
    } catch (err) {
      console.error(err);
    } finally {
      this.fetchFilms();
    }
  }

  onSubmit = async () => {
    const { currentFilm: { premiereDate, name, genre }, filmIdToEdit } = this.state;
    if (name === '' || premiereDate === null || genre === '' || !genres.includes(genre))
      return alert('Enter valid fields!')

    if (filmIdToEdit) {
      const body = {
        id: filmIdToEdit,
        premiereDate,
        name,
        genre
      }
      const options = {
        method: 'PUT',
        body: JSON.stringify(body),
        headers: {
          'Content-Type': 'application/json'
        }
      }
      try {
        const response = await fetch(`${API_URL}/films/${filmIdToEdit}`, options);
        if (response.status === 200) alert('success')
      } catch (err) {
        console.error(err)
      }
      this.setState({ filmIdToEdit: undefined })
    } else {
      const body = {
        premiereDate,
        name,
        genre
      }
      const options = {
        method: 'POST',
        body: JSON.stringify(body),
        headers: {
          'Content-Type': 'application/json'
        }
      }
      try {
        const response = await fetch(`${API_URL}/films/`, options);
        if (response.status === 200) alert('success')
      } catch (err) {
        console.error(err)
      }
    }
    this.resetCurrentFilm();
    this.fetchFilms();
  }

  renderFilms = () => {
    return this.state.films.map(({ id, name }) => (
      <div key={id} className="w-1/2 m-6 border-solid border-2 mr-64 flex justify-between">
        <div>{name}</div>
        <div>
          <button
            className="bg-green-400 px-4 my-4 mx-2"
            onClick={() => this.setFilmIdToEdit(id)}
          >
            edit
        </button>
          <button
            className="bg-red-500 px-4 my-4 mx-2"
            onClick={() => this.deleteById(id)}
          >
            delete
        </button>
        </div>
      </div>
    ))
  }

  render() {
    return (
      <div className="flex justify-center flex-col">
        {this.renderFilms()}
        <div className="flex justify-content">
          <div>
            <div className="md:flex md:items-center mb-6">
              <div className="md:w-1/3">
                <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" htmlFor="premiereDate">
                  Premiere date
                </label>
              </div>
              <div className="md:w-2/3">
                <DatePicker selected={this.state.currentFilm.premiereDate} onChange={date => this.setState({ currentFilm: { ...this.state.currentFilm, premiereDate: date } })} />
              </div>
            </div>
            <div className="md:flex md:items-center mb-6">
              <div className="md:w-1/3">
                <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" htmlFor="name">
                  Name
              </label>
              </div>
              <div className="md:w-2/3">
                <input
                  className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                  id="name"
                  type="text"
                  value={this.state.currentFilm.name}
                  onChange={e => this.setState({ currentFilm: { ...this.state.currentFilm, name: e.target.value } })} />
              </div>
            </div>

            <div className="md:flex md:items-center mb-6">
              <div className="md:w-1/3">
                <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" htmlFor="genre">
                  Genre
              </label>
              </div>
              <div className="md:w-2/3">
                <input
                  className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                  id="genre"
                  type="text"
                  value={this.state.currentFilm.genre}
                  onChange={e => this.setState({ currentFilm: { ...this.state.currentFilm, genre: e.target.value } })} />
              </div>
            </div>

            <button onClick={this.onSubmit} className="float-right bg-green-500">
              {this.state.filmIdToEdit ? 'Edit' : 'Add'}
            </button>
          </div>
        </div>
      </div>
    )
  }
}