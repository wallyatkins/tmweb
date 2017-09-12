import request from 'superagent';

let url = '/events';

export function getEvents (callback) {
  request
    .get(url)
    .end((err, resp) => {
      if (!err) {
        const events = [];
        let items = JSON.parse(resp.text);
        for (var i = 0; i < items.length; i++) {
          events.push({
            start: new Date(items[i].start),
            end: new Date(items[i].end),
            title: items[i].title
          });
        }
        callback(events);
      }
    });
}
