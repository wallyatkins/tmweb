import React from 'react';
import BigCalendar from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import moment from 'moment';
import { getEvents } from './tmcal';

let allViews = Object.keys(BigCalendar.views).map(k => BigCalendar.views[k]);

BigCalendar.momentLocalizer(moment);

class EventsCalendar extends React.Component{
  constructor () {
    super();
    this.state = {
      events: []
    };
  }
  componentDidMount () {
    getEvents((events) => {
      this.setState({events});
    });
  }
  render(){
    return (
      <BigCalendar
        {...this.props}
        events={this.state.events}
        startAccessor='start'
        endAccessor='end'
        views={allViews}
        style={{height: 1000}}
      />
    );
  }
}

export default EventsCalendar;