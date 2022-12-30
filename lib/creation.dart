import 'package:intl/intl.dart';
import 'dart:convert' as convert;
import 'package:flutter/material.dart';
import 'tree.dart';
import 'PageActivities.dart';

class PageCreation extends StatefulWidget {
  final Activity parent;
  PageCreation(this.parent);

  @override
  _PageCreationState createState() => _PageCreationState();
}

class _PageCreationState extends State<PageCreation> {
  late Activity parent;
  void initState() {
    super.initState();
    parent = widget.parent;
  }
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Creation ...'),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.home),
              onPressed: () {
                while (Navigator.of(context).canPop()) {
                  Navigator.of(context).pop();
                }
                PageActivities(0, "root");
              }
          ),
          //TODO other actions
        ],
      ),
      body: PageForm(),
    );
  }
}

class PageForm extends StatefulWidget {
  @override
  MyCustomForm createState() => MyCustomForm();
}

class MyCustomForm extends State<PageForm> {
  String formatValue = "Project";
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Padding(
            padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 16),
            child: DropdownButton(
              items: <String>['Project', 'Task']
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
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 16),
          child: TextFormField(
            decoration: const InputDecoration(
              border: UnderlineInputBorder(),
              labelText: 'Nombre',
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 16),
          child: TextFormField(
            decoration: const InputDecoration(
              border: UnderlineInputBorder(),
              labelText: 'Tags( separados por un espacio )',
            ),
          ),
        ),
      ],
    );
  }
}