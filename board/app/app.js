import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, IndexRoute, Link, browserHistory } from 'react-router';
import { createHistory } from 'history';
import BoardWrite from './components/BoardWrite.js';
import Board from './components/Board.js';

class App extends React.Component {
  render(){
      return (
          <div>
            <Board perPage={10}/>
            <Link to={'/write'}>Write</Link>
          </div>
      );
  }
}

class Write extends React.Component {
  render() {
    const { group, parentNum } = this.props.params;

    return (
      <div>
        <BoardWrite group={group} parentNum={parentNum}/>
      </div>
    )
  }
}

class Home extends React.Component {
  render() {
    return (
      <div>{this.props.children}</div>
    )
  }
}

ReactDOM.render(
  <Router history={browserHistory}>
    <Route path="/" component={Home}>
      <IndexRoute component={App} />
      <Route path="write" component={Write} />
      <Route path="write/:group/:parentNum" component={Write} />
    </Route>
  </Router>, document.getElementById('app')
);
