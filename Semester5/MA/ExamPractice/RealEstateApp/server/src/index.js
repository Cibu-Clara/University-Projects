var koa = require('koa');
var app = module.exports = new koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({ server });
const Router = require('koa-router');
const cors = require('@koa/cors');
const bodyParser = require('koa-bodyparser');

app.use(bodyParser());

app.use(cors());

app.use(middleware);

function middleware(ctx, next) {
  const start = new Date();
  return next().then(() => {
    const ms = new Date() - start;
    console.log(`${start.toLocaleTimeString()} ${ctx.response.status} ${ctx.request.method} ${ctx.request.url} - ${ms}ms`);
  });
}

const properties = [
  { id: 1, date: "2023-01-01", type: "Apartment", address: "123 Main St", bedrooms: 2, bathrooms: 1, price: 200000.00, area: 1000, notes: "Recently renovated" },
  { id: 2, date: "2023-01-02", type: "House", address: "456 Park Ave", bedrooms: 3, bathrooms: 2, price: 300000.01, area: 1500, notes: "Has a pool" },
  { id: 3, date: "2023-01-03", type: "Condo", address: "789 Ocean Blvd", bedrooms: 1, bathrooms: 1, price: 100000.00, area: 800, notes: "Ocean view" },
  { id: 4, date: "2023-01-04", type: "House", address: "246 River Rd", bedrooms: 4, bathrooms: 3, price: 400000.00, area: 1700, notes: "Has a fireplace" },
  { id: 5, date: "2023-01-05", type: "Apartment", address: "369 Hill St", bedrooms: 2, bathrooms: 1, price: 150000.00, area: 900, notes: "Close to downtown" },
  { id: 6, date: "2023-01-06", type: "Condo", address: "159 Mountain Ave", bedrooms: 1, bathrooms: 1, price: 110000.00, area: 700, notes: "Mountain view" },
  { id: 7, date: "2023-01-07", type: "House", address: "753 Lake St", bedrooms: 3, bathrooms: 2, price: 250000.00, area: 1300, notes: "Has a lake view" },
  { id: 8, date: "2023-01-08", type: "Apartment", address: "951 Forest Rd", bedrooms: 1, bathrooms: 1, price: 120000.99, area: 600, notes: "Close to the forest" },
  { id: 9, date: "2023-01-09", type: "House", address: "147 Valley Ave", bedrooms: 4, bathrooms: 3, price: 350000.09, area: 1900, notes: "Has a valley view" },
  { id: 10, date: "2023-01-10", type: "Apartment", address: "1234 Main Street", bedrooms: 2, bathrooms: 2, price: 250000.00, area: 1000, notes: "Beautiful apartment with a view of the park" },
  { id: 11, date: "2023-01-10", type: "House", address: "5678 Side Street", bedrooms: 3, bathrooms: 2, price: 325000.00, area: 1300, notes: "Spacious house with a large backyard" },
  { id: 12, date: "2023-01-15", type: "Condo", address: "9012 Ocean Drive", bedrooms: 1, bathrooms: 1, price: 200000.00, area: 800, notes: "Stylish condo with ocean views" },
  { id: 13, date: "2023-01-15", type: "Townhouse", address: "3456 Hill Road", bedrooms: 2, bathrooms: 2, price: 270000.00, area: 1100, notes: "Charming townhouse in a quiet neighborhood" },
  { id: 14, date: "2023-01-20", type: "Apartment", address: "7890 Park Avenue", bedrooms: 1, bathrooms: 1, price: 220000.00, area: 900, notes: "Luxurious apartment in the heart of the city" },
  { id: 15, date: "2023-01-20", type: "House", address: "1234 River Road", bedrooms: 4, bathrooms: 3, price: 350000.00, area: 1500, notes: "Beautiful house with a large backyard and river views" }
];

const router = new Router();
router.get('/properties', ctx => {
  ctx.response.body = properties.map(obj => { return { id: obj.id, address: obj.address } });
  ctx.response.status = 200;
});

router.get('/property/:id', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.params;
  const id = headers.id;
  // console.log("category: " + JSON.stringify(category));
  const index = properties.findIndex(obj => obj.id == id);
  if (index === -1) {
    const msg = "No property with id: " + id;
    console.log(msg);
    ctx.response.body = { text: msg };
    ctx.response.status = 404;
  } else {
    let obj = properties[index];
    ctx.response.body = obj;
    ctx.response.status = 200;
  }
});

router.get('/search', ctx => {
  ctx.response.body = properties;
  ctx.response.status = 200;
});

const broadcast = (data) =>
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(data));
    }
  });

router.post('/property', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.request.body;
  // console.log("body: " + JSON.stringify(headers));
  const date = headers.date;
  const type = headers.type;
  const address = headers.address;
  const bedrooms = headers.bedrooms;
  const bathrooms = headers.bathrooms;
  const price = headers.price;
  const area = headers.area;
  const notes = headers.notes;
  if (typeof date !== 'undefined'
    && typeof type !== 'undefined'
    && typeof address !== 'undefined'
    && typeof bedrooms !== 'undefined'
    && typeof bathrooms !== 'undefined'
    && typeof price !== 'undefined'
    && typeof area !== 'undefined'
    && typeof notes !== 'undefined') {
    const index = properties.findIndex(obj => obj.address == address && obj.type == type);
    if (index !== -1) {
      const msg = "Property already exists!";
      console.log(msg);
      ctx.response.body = { text: msg };
      ctx.response.status = 404;
    } else {
      let maxId = Math.max.apply(Math, properties.map(obj => obj.id)) + 1;
      let obj = {
        id: maxId,
        date,
        type,
        address,
        bedrooms,
        bathrooms,
        price,
        area,
        notes
      };
      properties.push(obj);
      broadcast(obj);
      ctx.response.body = obj;
      ctx.response.status = 200;
    }
  } else {
    const msg = "Missing or invalid date: " + date + " type: " + type
      + " address: " + address + " bedrooms: " + bedrooms
      + " bathrooms: " + bathrooms + " price: " + price
      + " area: " + area + " notes: " + notes;
    console.log(msg);
    ctx.response.body = { text: msg };
    ctx.response.status = 404;
  }
});

router.del('/property/:id', ctx => {
  // console.log("ctx: " + JSON.stringify(ctx));
  const headers = ctx.params;
  // console.log("body: " + JSON.stringify(headers));
  const id = headers.id;
  if (typeof id !== 'undefined') {
    const index = properties.findIndex(obj => obj.id == id);
    if (index === -1) {
      const msg = "No property with id: " + id;
      console.log(msg);
      ctx.response.body = { text: msg };
      ctx.response.status = 404;
    } else {
      let obj = properties[index];
      properties.splice(index, 1);
      broadcast(obj);
      ctx.response.body = obj;
      ctx.response.status = 200;
    }
  } else {
    ctx.response.body = { text: 'Id missing or invalid' };
    ctx.response.status = 404;
  }
});

app.use(router.routes());
app.use(router.allowedMethods());

const port = 2309;

server.listen(port, () => {
  console.log(`🚀 Server listening on ${port} ... 🚀`);
});