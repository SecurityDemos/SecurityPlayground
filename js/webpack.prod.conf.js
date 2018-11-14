const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const webpack = require('webpack');

module.exports = {
    entry: {
        app: './src/index'
    },
    output: {
        filename: '[name].[chunkhash].js',
        path: path.resolve(__dirname, '../static')
    },
    module: {
        rules: [
            {
                test: /\.(md|html)$/,
                use: {
                    loader: 'html-loader'
                }
            },
            {
                test: /\.ts?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
            {
                test: require.resolve('marked'),
                use: [{
                    loader: 'expose-loader',
                    options: 'marked'
                }]
            }
        ]
    },
    resolve: {
        extensions: ['.ts', '.js']
    },
    plugins: [
        new CleanWebpackPlugin(['app.*.js','static','font-roboto-local','index.html'], {root: path.resolve(__dirname, '../static')}),
        new webpack.NormalModuleReplacementPlugin(
            /environments\/environment\.ts/,
            'environment.prod.ts'
        ),
        new HtmlWebpackPlugin({
            template: './src/index.html'
        }),
        new CopyWebpackPlugin([
            {
                from: path.resolve(__dirname, './static'),
                to: 'static',
                ignore: ['.*']
            },
            {
                from: path.join(
                    path.resolve(__dirname, './node_modules/@webcomponents/webcomponentsjs/'),
                    '*.js'
                ),
                to: './webcomponentjs',
                flatten: true
            },
            {
                from: path.resolve(__dirname, './node_modules/@polymer/font-roboto-local/'),
                to: './font-roboto-local'
            }
        ]),
        new webpack.IgnorePlugin(/vertx/),
    ]
};
