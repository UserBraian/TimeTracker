import 'package:intl/intl.dart';
import 'dart:convert' as convert;
import 'package:flutter/material.dart';
import 'tree.dart';
import 'PageActivities.dart';
import 'requests.dart';


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
  final int id;
  final String name;

  PageIntervals(this.id, this.name);
  @override
  _PageIntervalsState createState() => _PageIntervalsState();
}

class _PageIntervalsState extends State<PageIntervals> {
  late int id;
  late Future<Tree> tree;
  bool isPlayPressed=false;

  @override
  void initState() {
    super.initState();
    id = widget.id;
    tree = getTree(id);
    // the root is a task and the children its intervals
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree>(future: tree, builder: (context, snapshot){
      if (snapshot.hasData) {
        return Scaffold(
            backgroundColor: Colors.white,
            appBar: AppBar(
              title: Text(snapshot.data!.root.name),
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
              itemCount: snapshot.data!.root.children.length, // number of intervals
              itemBuilder: (BuildContext context, int index) =>
                  Container(
                      decoration: BoxDecoration(
                        border: Border.all(
                          color: Colors.blueAccent,
                        ),
                        borderRadius: BorderRadius.all(Radius.circular(20)),
                      ),
                      child:_buildRow(snapshot.data!.root.children[index], index)),
              separatorBuilder: (BuildContext context, int index) =>
              const Divider(),
            ),
            floatingActionButton: FloatingActionButton(
                onPressed: () {
                  //_navigateDownCreation(tree.root);
                },
                backgroundColor: (snapshot.data!.root.active)?Colors.red:Colors.green,
                child: IconButton(icon: (snapshot.data!.root.active)?Icon(Icons.stop_rounded):Icon(Icons.play_arrow_rounded,),
                    tooltip:(snapshot.data!.root.active)?'Stop current interval':'Start new interval',
                    onPressed:(){setState((){
                      if(snapshot.data!.root.active==true){
                        snapshot.data!.root.active=false;
                      }else{
                        snapshot.data!.root.active=true;
                      }
                    });}
                )
            )
        );
      }else if (snapshot.hasError) {
        return Text("${snapshot.error}");
      }
      return Container(
          height: MediaQuery.of(context).size.height,
          color: Colors.amber,
          child: const Center(
            child: CircularProgressIndicator(),
          ));
    });

  }
    //const Icon(Icons.play_arrow_rounded),
  Widget _buildRow(Intervalo interval, int index) {
    String strDuration = Duration(seconds: interval.duration).toString().split('.').first;
    String strInitialDate = interval.initialDate.toString().split('.')[0];
    // this removes the microseconds part
    String strFinalDate = interval.finalDate.toString().split('.')[0];
    return ListTile(
      leading: Icon(Icons.query_builder_outlined),
      title: Text('from ${strInitialDate} to ${strFinalDate}'),
      trailing: Text('$strDuration'),
    );
  }
}
