var webpack = require("webpack");
var path = require("path");
const ExtractTextPlugin = require("extract-text-webpack-plugin");
module.exports = {
	entry: {
		"home/index" : './assets/entry/home/index.js',  // css and js 也可以定义为数组、分开
		"common/ngEdit" : './assets/entry/common/ngEdit.js',
		"common/gridList" : './assets/entry/common/gridList.js', //list页面 
		"common/codeMirror" : './assets/entry/common/codeMirror.js'
       //"bpm/task" : './assets/entry/bpm/task.js'  // css and js 也可以定义为数组、分开
	   },
	output: {
		path: path.resolve(__dirname, './build/'),
	    filename: '[name].js',//-v[hash:8]
	},
	module: {
	    loaders: [
	      //  {test: /\.css$/, loader: 'style-loader!css-loader'},
	      	{test: /\.css$/, loader: ExtractTextPlugin.extract({fallback: "style-loader",use: "css-loader"})},
	        {test: /\.(png|jpg|gif)$/,loader: 'url-loader?limit=8192&name=../images/[name]-[hash:8].[ext]'},
	        { test: /\.(woff|woff2|ttf|eot|svg|)$/, loader: "url-loader?limit=5000&name=../font/[name].[ext]" },
	        { test: require.resolve("jquery"), loader: "expose-loader?$!expose-loader?jQuery"}

	    ]
	} ,
	devtool: false,//'eval-source-map',// 开发环境配置。其他环境请设置为false  
	plugins:[
          new webpack.optimize.UglifyJsPlugin({
        	  exclude:/\.min\.js$/,
              compress: {warnings: false},
              output: { comments: false },
              sourceMap: true,
              mangle:false,
            }),
	 		 new webpack.BannerPlugin('AGILE-BPM 版权所有，翻版必究'),
	 		 new ExtractTextPlugin("[name].css"),
	        ],
    devServer:{
    	 inline:true,
    },
	        
}