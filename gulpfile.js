var gulp = require('gulp'),
// del = require('del'),
// copy = require('copy'),
    uglify = require('gulp-uglify'),
    concat = require('gulp-concat'),
    clean = require('gulp-clean'),
    rename = require('gulp-rename'),
    rev = require('gulp-rev'),
// domSrc = require('gulp-dom-src'),
    cheerio = require('gulp-cheerio'),
    revCollector = require('gulp-rev-collector');


gulp.task('clean-build', function () {
    return gulp.src('src/main/webapp/build/*', {read: false})
        .pipe(clean());
});

gulp.task('uglify-js', ['uglify-js-upLayer'], function () {
    return gulp.src(['src/main/webapp/js/views/**/*.js'])
        .pipe(uglify())
        .pipe(rename(function (path) {
            // path.basename += '.min';
            path.extname = '.js';
        }))
        .pipe(rev())
        .pipe(gulp.dest('src/main/webapp/build/js/views'))
        .pipe(rev.manifest())
        .pipe(gulp.dest('src/main/webapp/build/app'));
});

gulp.task('uglify-js-upLayer', ['uglify-common-js'], function () {
    return gulp.src(['src/main/webapp/js/*.js'])
        .pipe(uglify())
        .pipe(rename(function (path) {
            // path.basename += '.min';
            path.extname = '.js';
        }))
        .pipe(rev())
        .pipe(gulp.dest('src/main/webapp/build/js/'))
        .pipe(rev.manifest())
        .pipe(gulp.dest('src/main/webapp/build/app'));
});

gulp.task('uglify-common-js', ['clean-build'], function () {
    return gulp.src(['src/main/webapp/js/angular/angular-animate.js',
        'src/main/webapp/js/angular/angular-touch.min.js',
        'src/main/webapp/js/angular/ui-bootstrap-tpls-1.3.2.js',
        'src/main/webapp/js/angular/i18n/angular-locale_zh-cn.js',
        // 'src/main/webapp/nav-jquery.js',
        // 'src/main/webapp/nav-angular.js',
        // 'src/main/webapp/views/common/directives.js',
        'src/main/webapp/js/tree/bootstrap-treeview.js',
        'src/main/webapp/js/angular/angular-file-upload.js',
        'src/main/webapp/js/angular/angular-growl.js',
        'src/main/webapp/js/angular/angular-sanitize.js',
        'src/main/webapp/js/angular/w5cValidator.js',
        'src/main/webapp/js/file/FileSaver.js',
        'src/main/webapp/js/angular/angular-filter.js'])
        .pipe(uglify())
        .pipe(concat('app-full.js'))
        .pipe(rename(function (path) {
            path.basename += '.min';
            path.extname = '.js';
        }))
        .pipe(gulp.dest('src/main/webapp/js/views/common/'));
});

/*gulp.task('tags-js', function () {
 return domSrc({file: 'src/main/webapp/WEB-INF/views/common/taglibs.jsp', selector: 'script', attribute: 'src'})
 .pipe(gulp.dest('src/main/webapp/build/js'))
 .pipe(concat('app-full.min.js'))
 .pipe(uglify())
 .pipe(gulp.dest('src/main/webapp/build/js'));
 });*/


gulp.task('clean-jsp', function () {
    return gulp.src('src/main/webapp/WEB-INF/pages')
        .pipe(clean());
});


//替换jsp文件中引入文件
gulp.task('rev', ['clean-jsp', 'uglify-js'], function () {
    return gulp.src(['src/main/webapp/build/app/**/*.json', 'src/main/webapp/WEB-INF/views/**/*.jsp'])
        .pipe(revCollector({
            replaceReved: true,
            dirReplacements: {
                // '/js/': '/build/js/',
                /**
                 * 下面这条替换路径暂不开放
                 * 前置：需要将js中，依赖注入改造
                 */
                '/js/views/': '/build/js/views/'
            }
        }))
        .pipe(gulp.dest('src/main/webapp/WEB-INF/pages/'));
});

/*gulp.task('tags-jsp', function () {
 return gulp.src('src/main/webapp/WEB-INF/views/common/taglibs.jsp')
 .pipe(cheerio(function ($) {
 $('script').remove();
 $('body').append('<script src="app-full.min.js"></script>')
 }))
 .pipe(gulp.dest('src/main/webapp/WEB-INF/pages/common/'));
 });*/


gulp.task('default', ['rev'], function () {

})