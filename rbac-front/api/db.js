var sqlite3 = require('sqlite3').verbose();
var md5 = require('md5');

const DBSOURCE = "db.sqlite";

var db;

exports.db = db;

exports.open= async function(path) {
  return new Promise(function(resolve, reject) {
    this.db = new sqlite3.Database(path,
      (err) => {
        if (err) {
          // Cannot open database
          console.error(err.message);
          reject(err);
        }else{
          console.log('Connected to the SQlite database.');
      
          this.db.exec(`CREATE TABLE user_info (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name text,
            username text UNIQUE,
            password text,
            salt text,
            CONSTRAINT username_uni UNIQUE (username)
            );
            
            create table sys_role(
            id integer primary key autoincrement ,
            description txt ,
            name text unique
            );

            create table if not exists sys_permission(
            id integer primary key autoincrement ,
            description text ,
            name text unique ,
            url text);

            create table if not exists sys_role_permission(
            id integer primary key autoincrement ,
            permission_id integer not null ,
            role_id integer not null,
            constraint rp_pk unique (role_id, permission_id));

            create table if not exists sys_user_role(
            id integer primary key autoincrement ,
            uid integer not null ,
            role_id bigint not null,
            constraint ur_pk unique (role_id, uid));

    `,(err) => {
            if (err) {
              // Table already created
            }else{
              // Table just created, creating some rows
              // var insert = 'INSERT INTO user_info (`id`,`name`,`password`,`salt`,`username`) VALUES (?,?,?,?,?)';
              // db.run(insert, ["admin","admin@example.com",md5("admin123456")])
              // db.run(insert, [1, "user", md5("user123456"), 'salt', "user@example.com"])
              this.db.run('INSERT INTO `user_info` (`id`,`name`,`password`,`salt`,`username`) VALUES (1, \'管理员\',\'951cd60dec2104024949d2e0b2af45ae\', \'xbNIxrQfn6COSYn1/GdloA==\', \'admin\');')
              this.db.run('INSERT INTO `sys_role` (`id`,`description`,`name`) VALUES (1,\'管理员\',\'admin\')');
              this.db.run('INSERT INTO `sys_permission` (`id`,`description`,`name`,`url`) VALUES (1,\'查询用户\',\'userInfo:view\',\'/userList\')');
              this.db.run('INSERT INTO `sys_permission` (`id`,`description`,`name`,`url`) VALUES (2,\'增加用户\',\'userInfo:add\',\'/userAdd\')');
              this.db.run('INSERT INTO `sys_permission` (`id`,`description`,`name`,`url`) VALUES (3,\'删除用户\',\'userInfo:delete\',\'/userDelete\')');
              this.db.run('INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (1,1)');
              this.db.run('INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (2,1)');
              this.db.run('INSERT INTO `sys_user_role` (`role_id`,`uid`) VALUES (1,1)');
            }
          })
        }
      }
    )
  })
};

// any query: insert/delete/update
exports.run=function(query, params) {
  return new Promise(function(resolve, reject) {
    this.db.run(query, params,
      function(err)  {
        if(err) reject(err.message);
        else    resolve(true)
      })
  })
};

// first row read
exports.get=function(query, params) {
  return new Promise(function(resolve, reject) {
    this.db.get(query, params, function(err, row)  {
      if(err) reject("Read error: " + err.message);
      else {
        resolve(row)
      }
    })
  })
};

// set of rows read
exports.all=function(query, params) {
  return new Promise(function(resolve, reject) {
    if(params === undefined) params=[];
    
    this.db.all(query, params, function(err, rows)  {
      if(err) reject("Read error: " + err.message);
      else {
        resolve(rows)
      }
    })
  })
};

exports.exec=function(query, callback) {
  return new Promise(function(resolve, reject) {
    this.db.exec(query, function(err, rows)  {
      if(err) reject("Read error: " + err.message);
      else {
        resolve(rows)
      }
    })
  })
};

// each row returned one by one
exports.each=function(query, params, action) {
  return new Promise(function(resolve, reject) {
    var db = this.db;
    db.serialize(function() {
      db.each(query, params, function(err, row)  {
        if(err) reject("Read error: " + err.message);
        else {
          if(row) {
            action(row)
          }
        }
      });
      db.get("", function(err, row)  {
        resolve(true)
      })
    })
  })
};

exports.close=function() {
  return new Promise(function(resolve, reject) {
    this.db.close();
    resolve(true)
  })
};

(async function() {
  await exports.open(DBSOURCE);
  
  exports.db.on('trace', (ev) => {
    console.log(ev)
  });
}) ();
