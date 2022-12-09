// see Serializing JSON inside model classes in
// https://flutter.dev/docs/development/data-and-backend/json
//this week es del lunes al domingo aunue aun no sea domingo

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
      children.add(Interval.fromJson(jsonChild));
    }
  }
}


class Interval {
  late int id;
  DateTime? initialDate;
  DateTime? finalDate;
  late int duration;
  late bool active;

  Interval.fromJson(Map<String, dynamic> json)
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



void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TimeTracker',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        textTheme: const TextTheme(
            subtitle1: TextStyle(fontSize:20.0),
            bodyText2:TextStyle(fontSize:20.0)),
      ),
      home: PageActivities(0, "root"),
    );
  }
}


class PageActivities extends StatefulWidget {
  final int id;
  final String name;

  PageActivities(this.id, this.name);

  @override
  _PageActivitiesState createState() => _PageActivitiesState();
}

class _PageActivitiesState extends State<PageActivities> {
  late Tree tree;
  late int id;

  @override
  void initState() {
    super.initState();
    id = widget.id;
    tree = getTree(id);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(tree.root.name),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.home),
              onPressed: () {
                while (Navigator.of(context).canPop()) {
                  Navigator.of(context).pop();
                }
                PageActivities(0, "root");
              }
          ),
          IconButton(icon: Icon(Icons.report),
              onPressed: () {
                Navigator.of(context).push(MaterialPageRoute<void>(builder: (context) => PageReport(),
                ));
              }
          ),
        ],
      ),
      body: ListView.separated(
        // it's like ListView.builder() but better
        // because it includes a separator between items
        padding: const EdgeInsets.all(16.0),
        itemCount: tree.root.children.length,
        itemBuilder: (BuildContext context, int index) =>
            _buildRow(tree.root.children[index], index),
        separatorBuilder: (BuildContext context, int index) =>
        const Divider(),
      ),
    );
  }

  Widget _buildRow(Activity activity, int index) {
    String strDuration = Duration(seconds: activity.duration).toString().split('.').first;
    // split by '.' and taking first element of resulting list
    // removes the microseconds part
    assert (activity is Project || activity is Task);
    if (activity is Project) {
      return ListTile(
        title: Text('${activity.name}'),
        trailing: Text('$strDuration'),
        onTap: () => _navigateDownP(activity.id, activity.name),
        // TODO, navigate down to show children tasks and projectsw
      );
    } else {
      Task task = activity as Task;
      Widget trailing;
      trailing = Text('$strDuration');
      return ListTile(
        title: Text('${activity.name}'),
        trailing: trailing,
        onTap: () => _navigateDownIntervals(index),
        // TODO, navigate down to show intervals
        onLongPress: () {},
        // TODO start/stop counting the time for this task
      );
    }
  }
  void _navigateDownP(int id, String name) {
    Navigator.of(context).push(MaterialPageRoute<void>(builder: (context) => PageActivities(id, name)));
  }

  void _navigateDownIntervals(int childId) {
    Navigator.of(context)
        .push(MaterialPageRoute<void>(builder: (context) => PageIntervals())
    );

  }
}

Tree getTreeProject() {
  String strJson = "{"
       "\"name\":\"software design\", \"class\":\"project\", \"id\":1, \"initialDate\":\"2020-09-22 16:05:04\", \"finalDate\":\"2020-09-22 16:05:16\", \"duration\":16,"
      "\"activities\": [ " "{ \"name\":\"testing\", \"class\":\"project\", \"id\":7, \"initialDate\": null, \"finalDate\":null, \"duration\":0 },"
      "{ \"name\":\"testing2\", \"class\":\"project\", \"id\":8, \"initialDate\": null, \"finalDate\":null, \"duration\":0 }""]"
      "}";
  Map<String, dynamic> decoded = convert.jsonDecode(strJson);
  Tree tree = Tree(decoded);
  return tree;
}

Tree getTreeTask() {
  String strJson = "{"
      "\"name\":\"transportation\",\"class\":\"task\", \"id\":6, \"active\":false, \"initialDate\":\"2020-09-22 13:36:08\", \"finalDate\":\"2020-09-22 13:36:34\", \"duration\":10,"
      "\"intervals\":["
      "{\"class\":\"interval\", \"id\":7, \"active\":false, \"initialDate\":\"2020-09-22 13:36:08\", \"finalDate\":\"2020-09-22 13:36:14\", \"duration\":6},"
      "{\"class\":\"interval\", \"id\":8, \"active\":false, \"initialDate\":\"2020-09-22 13:36:30\", \"finalDate\":\"2020-09-22 13:36:34\", \"duration\":4}"
      "]}";
  Map<String, dynamic> decoded = convert.jsonDecode(strJson);
  Tree tree = Tree(decoded);
  return tree;
}

class PageIntervals extends StatefulWidget {
  @override
  _PageIntervalsState createState() => _PageIntervalsState();
}

class _PageIntervalsState extends State<PageIntervals> {
  late Tree tree;

  @override
  void initState() {
    super.initState();
    tree = getTreeTask();
    // the root is a task and the children its intervals
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(tree.root.name),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.home),
              onPressed: () {
                while (Navigator.of(context).canPop()) {
                  Navigator.of(context).pop();
                }
                PageActivities(0, "root");
              } // TODO go home page = root
          ),
          //TODO other actions
        ],
      ),
      body: ListView.separated(
        // it's like ListView.builder() but better because it includes a
        // separator between items
        padding: const EdgeInsets.all(16.0),
        itemCount: tree.root.children.length, // number of intervals
        itemBuilder: (BuildContext context, int index) =>
            _buildRow(tree.root.children[index], index),
        separatorBuilder: (BuildContext context, int index) =>
        const Divider(),
      ),
    );
  }

  Widget _buildRow(Interval interval, int index) {
    String strDuration = Duration(seconds: interval.duration).toString().split('.').first;
    String strInitialDate = interval.initialDate.toString().split('.')[0];
    // this removes the microseconds part
    String strFinalDate = interval.finalDate.toString().split('.')[0];
    return ListTile(
      title: Text('from ${strInitialDate} to ${strFinalDate}'),
      trailing: Text('$strDuration'),
    );
  }
}

class PageReport extends StatefulWidget {

  @override
  _PageReportState createState() => _PageReportState();

}

class _PageReportState extends State<PageReport> {
  String periodValue = "Today";
  late DateTime fromValue;
  late DateTime toValue;
  late DateTimeRange fromTo;
  late DateTime today;
  String contentValue = "Brief";
  String formatValue = "Web page";

  @override
  void initState() {
    super.initState();
    today = DateTime.now();
    fromValue = today.subtract(new Duration(days:7));
    periodValue = "Last week";
    toValue = today;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Report"),
        ),
        body: Container(
            alignment: Alignment.center,
            padding: const EdgeInsets.all(30),
            child: Column(
              children: <Widget>[
                Row(
                    children: <Widget>[
                      Container(
                        width: 90.0,
                        child: Text("Period"),
                        padding: const EdgeInsets.all(10),
                      ),
                      DropdownButton(
                        items: <String>['Last week', 'This week', 'Yesterday',
                          'Today', 'Other']
                            .map<DropdownMenuItem<String>>((String value) {
                          return DropdownMenuItem<String>(
                            value: value,
                            child: Text(value),
                          );
                        }).toList(),
                        value: periodValue,
                        onChanged: (String? newValue) { setState(() {
                        periodValue = newValue!;
                        updateDates();
                      }); },
                      ),
                    ]
                ),
                Row(
                    children: <Widget>[
                      Container(
                        width: 90.0,
                        child: Text("From"),
                        padding: const EdgeInsets.all(10),
                      ),
                      Text(DateFormat('yyyy-MM-dd').format(fromValue)),
                      IconButton(onPressed: _pickFromDate, icon: Icon(Icons.date_range, color: Colors.blue))
                    ]
                ),
                Row(
                    children: <Widget>[
                      Container(
                        width: 90.0,
                        child: Text("To"),
                        padding: const EdgeInsets.all(10),
                      ),
                      Text(DateFormat('yyyy-MM-dd').format(toValue)),
                      IconButton(onPressed: _pickToDate, icon: Icon(Icons.date_range, color: Colors.blue))
                    ]
                ),
                Row(
                    children: <Widget>[
                      Container(
                        width: 100.0,
                        child: Text("Content"),
                        padding: const EdgeInsets.all(10),
                      ),
                      DropdownButton(
                        items: <String>['Brief', 'Detailed', 'Statistic']
                            .map<DropdownMenuItem<String>>((String value) {
                          return DropdownMenuItem<String>(
                            value: value,
                            child: Text(value),
                          );
                        }).toList(),
                        value: contentValue,
                        onChanged: (String? newValue) { setState(() {
                          contentValue = newValue!;
                        }); },
                      ),
                    ]
                ),
                Row(
                    children: <Widget>[
                      Container(
                        width: 90.0,
                        child: Text("Format"),
                        padding: const EdgeInsets.all(10),
                      ),
                      DropdownButton(
                        items: <String>['Web page', 'PDF', 'Text']
                            .map<DropdownMenuItem<String>>((String value) {
                          return DropdownMenuItem<String>(
                            value: value,
                            child: Text(value),
                          );
                        }).toList(),
                        value: formatValue,
                        onChanged: (String? newValue) {
                          setState(() {
                            formatValue = newValue!;
                          });
                        },
                      )
                    ]
                ),
                Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Container(
                        width: 125.0,
                        padding: const EdgeInsets.all(10),
                        child: TextButton(
                          style: TextButton.styleFrom(
                              textStyle: const TextStyle(fontSize: 19, fontWeight:
                              FontWeight.bold)
                          ),
                          onPressed: () {},
                          child: const Text('Generate'),
                        ),
                      )
                    ]
                )
              ]
            )
        )
    );
  }
  void updateDates() {
    switch (periodValue) {
      case "Last week":
        fromValue = today.subtract(new Duration(days:7));
        toValue = today;
        break;
      case "This week":
        var mondayThisWeek = DateTime(today.year, today.month,
            today.day - today.weekday + 1);
        fromValue = mondayThisWeek;
        toValue = today;
        break;
      case "Yesterday":
        var yesterday = today.subtract(Duration(days:1));
        fromValue = yesterday;
        toValue = today;
        break;
      case "Today":
        fromValue = today;
        toValue = today;
        break;
      case "Other":
        fromValue = today;
        toValue = today;
        break;
      default:
        break;
    }
  }

  _pickFromDate() async {
    DateTime? newStart = await showDatePicker(
      context: context,
      firstDate: DateTime(today.year - 5),
      lastDate: DateTime(today.year + 5),
      initialDate: today,
    );
    DateTime end = toValue; // the present To date
    if (end.difference(newStart!) >= Duration(days: 0)) {
      fromTo = DateTimeRange(start: newStart, end: end);
      // x is where you store the (From,To) DateTime pairs
      // associated to the ’Other’ option
      fromValue = newStart;
      setState(() {
        periodValue = "Other"; // to redraw the screen
      });
    } else {
      _showAlertDates();
    }
  }

  _pickToDate() async {
    DateTime? newEnd = await showDatePicker(
      context: context,
      firstDate: DateTime(today.year - 5),
      lastDate: DateTime(today.year + 5),
      initialDate: today,
    );
    DateTime start = fromValue; // the present To date
    if (newEnd!.difference(start) >= Duration(days: 0)) {
      fromTo = DateTimeRange(start: start, end: newEnd);
      // x is where you store the (From,To) DateTime pairs
      // associated to the ’Other’ option
      toValue = newEnd;
      setState(() {
        periodValue = "Other"; // to redraw the screen
      });
    } else {
      _showAlertDates();
    }
  }

  Future<void> _showAlertDates() async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Range dates'),
          content: SingleChildScrollView(
            child: ListBody(
              children: const <Widget>[
                Text('The From date is after the To date.'),
                Text('Please, select a new date.'),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('ACCEPT'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

}



