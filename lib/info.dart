import 'package:intl/intl.dart';
import 'dart:convert' as convert;
import 'package:flutter/material.dart';
import 'tree.dart';
import 'PageActivities.dart';
import 'requests.dart';

class PageInfor extends StatefulWidget {
  final int id;
  final String name;
  PageInfor(this.id, this.name);

  @override
  _PageInforState createState() => _PageInforState();
}

class _PageInforState extends State<PageInfor> {
  late Future<Tree> tree;
  late int id;

  @override
  void initState() {
    super.initState();
    id = widget.id;
    tree = getTree(id);
  }

  Widget build(BuildContext context) {
    return FutureBuilder<Tree>(future: tree, builder: (context, snapshot){
      if (snapshot.hasData) {
        return Scaffold(
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
            body: Container(
                alignment: Alignment.center,
                padding: const EdgeInsets.all(30),
                child: Column(
                    children: <Widget>[
                      Row(
                          children: <Widget>[Text('Nombre: ${snapshot.data!.root.name}')]
                      ),
                      Row(
                          children: <Widget>[Text('Fecha Inicial: ${snapshot.data!.root.initialDate}')]
                      ),
                      Row(
                          children: <Widget>[Text('Fecha Final: ${snapshot.data!.root.finalDate}')]
                      ),
                      Row(
                          children: <Widget>[Text('Duracion: ${snapshot.data!.root.duration}')]
                      ),
                      Row(
                          children: <Widget>[Text('tags: ${snapshot.data!.root.tags}')]
                      ),
                      Row(
                          children: <Widget>[
                            IconButton(icon: Icon(Icons.delete), onPressed:(){/*delete activity*/}),
                            Text(' '),
                            IconButton(icon: Icon(Icons.edit), onPressed:(){_openPopup(context);})]

                      )
                    ]
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
  void _openPopup(context) {
    //Navigator.of(context).push(MaterialPageRoute<void>(builder: (context) => PageFormulario(id, name)));
    showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: const Text("Edit Information"),
        //content: const Text("You have raised a Alert Dialog Box"),
        actions: <Widget>[
          TextButton(
            onPressed: () {
              Navigator.of(ctx).pop();
            },
            child: Container(
              color: Colors.grey,
              padding: const EdgeInsets.all(14),
              child: const Text("okay"),
            ),
          ),
        ],
      ),
    );
  }
}