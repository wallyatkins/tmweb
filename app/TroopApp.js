import React from 'react';
import AppBar from 'material-ui/AppBar';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';

export default class TroopApp extends React.Component {
    
  constructor(props) {
    super(props);
    this.state = {open: false};
  }
  
  handleToggle() { this.setState({open: !this.state.open}); }
  
  render() {
    return (
      <AppBar
        title = { "Troop 200 - Yorktown, VA" }
        onLeftIconButtonTouchTap = { this.handleToggle.bind(this) } >
        <Drawer open={this.state.open}>
          <MenuItem>Calendar</MenuItem>
          <MenuItem>Photos</MenuItem>
          <MenuItem>Library</MenuItem>
          <MenuItem>History</MenuItem>
          <MenuItem>About</MenuItem>
        </Drawer>
      </AppBar>
    );
  }
 
}

