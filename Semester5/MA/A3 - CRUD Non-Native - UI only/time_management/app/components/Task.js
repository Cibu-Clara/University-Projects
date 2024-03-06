import React from 'react'
import { Text, StyleSheet, Dimensions, TouchableOpacity } from 'react-native'
import colors from '../resources/colors';

const Task = ({item, onPress}) => {
    const {title, priority, descr} = item;

    return (
      <TouchableOpacity onPress={onPress} style={styles.container}>
        <Text style={styles.title} numberOfLines={2}>{title}</Text>
        <Text numberOfLines={1}>{`Priority level: ${priority}`}</Text>
        <Text numberOfLines={3}>{`Description: ${descr}`}</Text>
      </TouchableOpacity>
    );
};

const width = Dimensions.get('window').width - 40;

const styles = StyleSheet.create({
    container: {
        backgroundColor: colors.PRIMARY,
        width: width / 2 - 10,
        padding: 8,
        borderRadius: 10
    },
    title: {
        fontWeight: 'bold',
        fontSize: 10,
        color: colors.LIGHT
    }
})

export default Task;
