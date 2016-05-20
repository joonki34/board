import React from 'react';
import { Link, browserHistory } from 'react-router';

export default class PostList extends React.Component {
    render(){
      let posts = this.props.data.map(function(post, index) {
        let depth = post.depth;
        let replySymbol = "ㄴ ";

        return (
          <div key={index}>
            <Link to={`/write/${post.group}/${post.id}`}>{replySymbol.repeat(depth) + post.content}</Link>
          </div>
        );
      });

      return (
        <div id="posts" className="postList">
            {posts}
        </div>
      );
    }
}
