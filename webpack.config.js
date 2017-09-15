var packageJSON = require('./package.json');
var path = require('path');
var webpack = require('webpack');

const PATHS = {
  build: path.join(__dirname, 'target', 'classes', 'public')
};

module.exports = {
  entry: './app/index.js',

  output: {
    path: PATHS.build,
    publicPath: '/assets/',
    filename: 'bundle.js'
  },

  devServer: {
    contentBase: "./src/main/resources/public",
    compress: true,
    port: 9000
  },

  module: {
    loaders: [
      {
        test: /\.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/,
        query: {
          presets: ['es2015','react']
        }
      },
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader'
      }
    ]
  }
};
