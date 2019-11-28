import React from 'react';
import { Switch, Route, BrowserRouter } from 'react-router-dom';
import Cinema from './components/Cinema';
import AdminPage from './components/AdminPage';

export default () => (
  <BrowserRouter>
    <Switch>
      <Route exact path='/' component={Cinema} />
      <Route path='/admin' component={AdminPage} />
    </Switch>
  </BrowserRouter>
)
