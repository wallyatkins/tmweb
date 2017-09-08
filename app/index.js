import React from 'react'
import BigCalendar from 'react-big-calendar'
import 'react-big-calendar/lib/css/react-big-calendar.css'
import moment from 'moment'
import { render } from 'react-dom'
import { getEvents } from './tmcal'

let allViews = Object.keys(BigCalendar.views).map(k => BigCalendar.views[k])

BigCalendar.momentLocalizer(moment)

class App extends React.Component{
  constructor () {
    super()
    this.state = {
      events: []
    }
  }
  componentDidMount () {
    getEvents((events) => {
      this.setState({events})
    })
  }
  render(){
    return (
      <BigCalendar
        {...this.props}
        events={this.state.events}
        startAccessor='start'
        endAccessor='end'
        views={allViews}
        defaultDate={new Date(2015, 3, 1)}
        style={{height: 800}}
      />
    )
  }
}

render(<App/>, document.getElementById('root'))
