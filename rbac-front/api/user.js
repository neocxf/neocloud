var express = require("express");
var router = express();
var db = require("./db");

router.get("/api/users", async (req, res, next) => {
  var sql = "select * from user_info";
  var params = [];
  var result;
  
  try {
    result = await db.all(sql, params);
  } catch (e) {
    multiCallback(res, null, e);
  }
  
  multiCallback(res, result, null);
  
});

router.get("/api/users/one", async (req, res, next) => {
  const sql = "select * from user_info where id=?";
  const params = [req.query.id];
  let result;
  
  try {
    result = await db.get(sql, params);
  } catch (e) {
    callback(res, null, e);
  }
  
  callback(res, result, null);
  
});

router.get("/api/users/preauthorize", async (req, res, next) => {
  const user = req.query.user;
  
  console.log('/api/users?user=' + user);
  
  let allRoles;
  let userRoles;
  try {
    allRoles = await db.all("select * from sys_role", []);
    userRoles = await db.all("select sr.id from sys_role sr left join sys_user_role sur on sr.id = sur.role_id where sur.uid=?", [user]);
    console.log(userRoles);
    
  } catch (e) {
    return res.status(500).json({"error":e.message, status: 500});
  }
  
  return res.json({
    "status": 200,
    "message": "success",
    "data": {allRoles, userRoles}
  })
  
});

router.post("/api/users/authorize", async (req, res, next) => {
  
  let userId = req.body.userId;
  let roleIds = req.body['roleIds'].split(',');
  
  const values = roleIds.map(roleId => `(${userId}, ${roleId})`).join(',');
  
  const sql = `delete from sys_user_role where uid=${userId}; insert into sys_user_role (uid, role_id) values ${values}`;
  
  let result;
  
  try {
    result = await db.exec(sql);
  } catch (e) {
    console.log(e);
    res.status(500).json({"error": e.message, "status": 500});
    return;
  }
  
  return res.json({
    "message": "success",
    "data": result,
    status: 200
  })
});

router.post("/api/users/", async (req, res, next) => {
  var errors=[];
  if (!req.body.username){
    errors.push("No user name specified");
  }
  
  if (!req.body.name){
    errors.push("No name specified");
  }
  
  if (errors.length){
    res.status(500).json({"error":errors.join(",")});
    return;
  }
  var data = {
    name: req.body.name,
    username: req.body.username,
    password: req.body.password,
    salt: req.body.salt
  };
  var sql ='INSERT INTO user_info (name, username, password, salt) VALUES (?,?,?,?)';
  var params =[data.name, data.username, data.password, data.salt];
  
  let result ;
  
  try {
    result = await db.run(sql, params);
  } catch (e) {
    res.status(500).json({"error": err.message, "status": 500});
    return;
  }
  return res.json({
    "status": 200,
    "message": "success",
    "data": data,
    "id" : this.lastID
  });
  
});

router.put("/api/users/:id", async (req, res, next) => {
  let data = {
    name: req.body.name,
    username: req.body.username,
    password: req.body.password,
    salt: req.body.salt,
  };
  
  let result;
  
  try {
    result = await db.run(
      `UPDATE user_info set
           name = coalesce(?,name),
           password = COALESCE(?,password),
           salt = COALESCE(?,salt)
           WHERE id = ?`,
      [data.name, data.password, data.salt, req.params.id]
    );
  } catch (e) {
    res.status(500).json({"error": e.message});
    return;
  }
  
  return res.json({
    status: 200,
    message: "success",
    data: result
  })
});

router.get("/api/users", async (req, res, next) => {

  var sql = "select * from user_info where id = ?";
  var params = [req.query.id];
  let result;
  
  try {
    result = await db.get(sql, params);
  } catch (e) {
    res.status(400).json({"error": e.message});
    return;
  }
  
  return res.json({
    status: 200,
    "message": "success",
    "data": result
  })
});

router.get("/api/roles", async (req, res, next) => {
  var sql = "select * from sys_role";
  var params = [];
  
  let result;
  
  try {
    result = await db.all(sql, params)
  } catch (e) {
    multiCallback(res, null, e);
  }
  
  multiCallback(res, result, null);
  
});

router.get("/api/roles/one", async (req, res, next) => {
  var sql = "select * from sys_role where id=?";
  var params = [req.query.id];
  
  let result;
  
  try {
    result = await db.get(sql, params)
  } catch (e) {
    callback(res, null, e);
  }
  
  callback(res, result, null);
  
});

router.post("/api/roles/", async (req, res, next) => {
  var errors=[];
  if (!req.body.name){
    errors.push("No role name specified");
  }

  if (errors.length){
    res.status(500).json({"error":errors.join(",")});
    return;
  }
  var data = {
    name: req.body.name,
    description: req.body.description
  };
  var sql ='INSERT INTO sys_role (name, description) VALUES (?,?)';
  var params =[data.name, data.description];
  let result;
  
  try {
    result = await db.run(sql, params);
  } catch (e) {
    res.status(500).json({"error": e.message, "status": 500});
    return;
  }
  
  return res.json({
    "status": 200,
    "message": "success",
    "data": result,
    "id": this.lastID
  })
});

router.put("/api/roles/:id", async (req, res, next) => {
  let data = {
    name: req.body.name,
    description: req.body.description,
  };
  
  console.log(data);
  
  let result;
  
  try {
    result = await db.run(
      `UPDATE sys_role set
           name = coalesce(?,name),
           description = COALESCE(?,description)
           WHERE id = ?`,
      [data.name, data.description, req.params.id]
    );
  } catch (e) {
    res.status(500).json({"error": e.message});
    return;
  }
  
  return res.json({
    status: 200,
    message: "success",
    data: result
  })
});

router.get("/api/roles", async (req, res, next) => {
  var sql = "select * from sys_role where id=?";
  var params = [req.query.id];
  let result;
  
  try {
    result = await db.get(sql, params);
  } catch (e) {
    res.status(400).json({"error": e.message});
    return;
  }
  
  return res.json({
    "message": "success",
    "data": result
  })
});

router.post("/api/roles/authorize", async (req, res, next) => {
  
  let roleId = req.body.roleId;
  let permIds = req.body['permIds'].split(',');
  
  const values = permIds.map(permId => `(${roleId}, ${permId})`).join(',');
  
  const sql = `delete from sys_role_permission where role_id=${roleId}; insert into sys_role_permission (role_id, permission_id) values ${values}`;
  
  let result;
  
  try {
    result = await db.exec(sql);
  } catch (e) {
    console.log(e);
    res.status(500).json({"error": e.message, "status": 500});
    return;
  }
  
  return res.json({
    "message": "success",
    "data": result,
    status: 200
  })
});

router.delete("/api/roles/:id", async (req, res, next) => {
  const sql = "delete from sys_role where id=? and not exists(select role_id from sys_user_role where role_id=?)";
  const params = [req.params.id, req.params.id];
  let result;
  
  try {
    result = await db.run(sql, params);
  } catch (e) {
    res.status(400).json({"error": e.message});
    return;
  }
  
  return res.json({
    "message": "success",
    "data": result
  })
});

router.get("/api/permissions/preauthorize", async (req, res, next) => {
  var role = req.query.role;
  
  var allPerms;
  var rolePerms;
  try {
    allPerms = await db.all("select * from sys_permission", []);
    rolePerms = await db.all("select sp.id from sys_permission sp left join sys_role_permission srp on sp.id = srp.permission_id where srp.role_id=?", [role]);
  } catch (e) {
    res.status(400).json({"error":e.message});
  }
  
  return res.json({
    "status": 200,
    "message": "success",
    "data": {allPerms, rolePerms}
  })
  
});

router.get("/api/permissions", async (req, res, next) => {
  
  let allPerms;
  try {
    allPerms = await db.all("select * from sys_permission", []);
  } catch (e) {
    multiCallback(res, null, e);
  }
  
  multiCallback(res, allPerms, null);
  
});

function multiCallback(res, rows, err) {
  if (err != null) {
    return res.status(400).json({"error":err.message});
  }
  
  res.json({
    "status": 200,
    "message":"success",
    "data": {rows, total: rows.length}
  })
}

function callback(res, row, err) {
  if (err != null) {
    return res.status(500).json({"error":err.message, "status": 500});
  }
  
  res.json({
    "status": 200,
    "message":"success",
    "data": row
  })
}

module.exports=router;