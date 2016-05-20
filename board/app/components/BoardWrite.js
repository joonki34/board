import React from 'react';
import { Link, browserHistory } from 'react-router';
import $ from 'jquery';

export default class BoardWrite extends React.Component {
  constructor() {
    super();
    this.state = {
      content : ""
    };
  }

  handleInput(event) {
    this.setState({content: event.target.value});
  }

  handleClick(event) {
    this.savePostToServer();
  }

  savePostToServer() {
    let data = {
      content: this.state.content,
      group: this.props.group,
      parent_num: this.props.parentNum,
      user: {
        id: 1
      }
    };
    console.log(data);
    $.ajax({
      url      : "http://localhost:8080/posts",
      data     : JSON.stringify(data),
      contentType : "application/json",
      type     : 'POST',
      success: result => {
        browserHistory.push('/');
      },

      error: (xhr, status, err) => {
        //console.error(this.props.url, status, err.toString());
      }
    });
  }

  render() {
    return (
      <div>
        <input type="text" onChange={this.handleInput.bind(this)} value={this.state.content}/>
        <input type="button" value="submit" onClick={this.handleClick.bind(this)} />
        <Link to={'/'}>Go to home</Link>
      </div>
    )
  }
}
