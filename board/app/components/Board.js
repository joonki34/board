import React from 'react';
import ReactPaginate from 'react-paginate';
import PostList from './PostList.js'
import $ from 'jquery';

export default class Board extends React.Component {
    constructor() {
      super();

      this.state = {
        data: [],
        offset: 0
      };
    }

    loadPostsFromServer() {
      $.ajax({
        url      : "http://localhost:8080/posts",
        data     : {limit: this.props.perPage, offset: this.state.offset},
        dataType : 'json',
        type     : 'GET',
        success: result => {
          this.setState({data: result.data, pageNum: Math.ceil(result.total_count / result.limit)});
        },
        error: (xhr, status, err) => {
          //console.error(this.props.url, status, err.toString());
        }
      });
    };

    componentDidMount() {
      this.loadPostsFromServer();
    }

    handlePageClick(data) {
      let selected = data.selected;
      let offset = Math.ceil(selected * this.props.perPage);

      this.setState({offset: offset}, () => {
        this.loadPostsFromServer();
      });
    };

    render(){
        return (
            <div>
              <PostList data={this.state.data} />
              <ReactPaginate previousLabel={"previous"}
                       nextLabel={"next"}
                       breakLabel={<a href="">...</a>}
                       pageNum={this.state.pageNum}
                       marginPagesDisplayed={2}
                       pageRangeDisplayed={5}
                       clickCallback={this.handlePageClick.bind(this)}
                       containerClassName={"pagination"}
                       subContainerClassName={"pages pagination"}
                       activeClassName={"active"} />
            </div>
        );
    }
}
