import 'package:intl/intl.dart';
import 'dart:convert' as convert;
import 'package:flutter/material.dart';

final DateFormat _dateFormatter = DateFormat("yyyy-MM-dd HH:mm:ss");

abstract class Activity {
  late int id;
  late String name;
  DateTime? initialDate;
  DateTime? finalDate;
  late int duration;
  List<dynamic> children = List<dynamic>.empty(growable: true);

  Activity.fromJson(Map<String, dynamic> json)
      : id = json['id'],
        name = json['name'],
        initialDate = json['initialDate']==null ? null : _dateFormatter.parse(json['initialDate']),
        finalDate = json['finalDate']==null ? null : _dateFormatter.parse(json['finalDate']),
        duration = json['duration'];
}


class Project extends Activity {
  Project.fromJson(Map<String, dynamic> json) : super.fromJson(json) {
    if (json.containsKey('activities')) {
      // json has only 1 level because depth=1 or 0 in time_tracker
      for (Map<String, dynamic> jsonChild in json['activities']) {
        if (jsonChild['class'] == "project") {
          children.add(Project.fromJson(jsonChild));
          // condition on key avoids infinite recursion
        } else if (jsonChild['class'] == "task") {
          children.add(Task.fromJson(jsonChild));
        } else {
          assert(false);
        }
      }
    }
  }
}


class Task extends Activity {
  late bool active;
  Task.fromJson(Map<String, dynamic> json) : super.fromJson(json) {
    active = json['active'];
    for (Map<String, dynamic> jsonChild in json['intervals']) {
      children.add(Intervalo.fromJson(jsonChild));
    }
  }
}


class Intervalo {
  late int id;
  DateTime? initialDate;
  DateTime? finalDate;
  late int duration;
  late bool active;

  Intervalo.fromJson(Map<String, dynamic> json)
      : id = json['id'],
        initialDate = json['initialDate']==null ? null : _dateFormatter.parse(json['initialDate']),
        finalDate = json['finalDate']==null ? null : _dateFormatter.parse(json['finalDate']),
        duration = json['duration'],
        active = json['active'];
}


class Tree {
  late Activity root;

  Tree(Map<String, dynamic> dec) {
    // 1 level tree, root and children only, root is either Project or Task. If Project
    // children are Project or Task, that is, Activity. If root is Task, children are Interval.
    assert (dec['class'] == "project" || dec['class']=='task');
    if (dec['class'] == "project") {
      root = Project.fromJson(dec);
    } else {
      root = Task.fromJson(dec);
    }
  }
}


Tree getTree(int id) {
  String strJson = "{"
      "\"name\":\"root\", \"class\":\"project\", \"id\":0, \"initialDate\":\"2020-09-22 16:04:56\", \"finalDate\":\"2020-09-22 16:05:22\", \"duration\":26,"
      "\"activities\": [ "
      "{ \"name\":\"software design\", \"class\":\"project\", \"id\":1, \"initialDate\":\"2020-09-22 16:05:04\", \"finalDate\":\"2020-09-22 16:05:16\", \"duration\":16,"
      "\"activities\": [ " "{ \"name\":\"testing\", \"class\":\"project\", \"id\":7, \"initialDate\": null, \"finalDate\":null, \"duration\":0 },"
      "{ \"name\":\"testing2\", \"class\":\"project\", \"id\":8, \"initialDate\": null, \"finalDate\":null, \"duration\":0 }""] "" },"
      "{ \"name\":\"software testing\", \"class\":\"project\", \"id\":2, \"initialDate\": null, \"finalDate\":null, \"duration\":0 },"
      "{ \"name\":\"databases\", \"class\":\"project\", \"id\":3,  \"finalDate\":null, \"initialDate\":null, \"duration\":0 },"
      "{ \"name\":\"transportation\", \"class\":\"task\", \"id\":6, \"active\":false, \"initialDate\":\"2020-09-22 16:04:56\", \"finalDate\":\"2020-09-22 16:05:22\", \"duration\":10, \"intervals\":[] }"
      "] "
      "}";
  Map<String, dynamic> decoded = convert.jsonDecode(strJson);
  Tree tree =Tree(decoded);
  for(Map<String, dynamic> act in decoded["activities"]){
    if(act["id"] == id){
      tree = Tree(act);
    }
    if(act.containsKey("activities")){
      for(Map<String, dynamic> act2 in act["activities"]){
        if(act2["id"] == id){
          tree = Tree(act2);
        }
      }
    }
  }
  return tree;
}

testLoadTree() {
  Tree tree = getTree(0);
  print("root name ${tree.root.name}, duration ${tree.root.duration}");
  for (Activity act in tree.root.children) {
    print("child name ${act.name}, duration ${act.duration}");
    if(act.children.length != 0 ){
      for (Activity act2 in act.children) {
        print("child2 name ${act2.name}, duration ${act2.duration}");
      }
    }
  }
}


//void main() {
//testLoadTree();
//}