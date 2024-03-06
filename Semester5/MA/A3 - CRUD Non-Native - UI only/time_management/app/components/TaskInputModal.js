import React, { useEffect, useState } from 'react'
import { View, StyleSheet, Modal, StatusBar, TextInput, TouchableWithoutFeedback, Keyboard, Alert } from 'react-native'
import colors from '../resources/colors';
import RoundIconBtn from './RoundIconBtn';

const TaskInputModal = ({visible, onClose, onSubmit, task, isEdit}) => {
    const [title, setTitle] = useState('');
    const [priority, setPriority] = useState('');
    const [descr, setDescr] = useState('');

    const handleOnChangeText = (text, valueFor) => {
        if (valueFor === 'title') setTitle(text);
        if (valueFor === 'priority') setPriority(text);
        if (valueFor === 'descr') setDescr(text);
    };

    useEffect(() => {
        if(isEdit) {
            setTitle(task.title);
            setPriority(task.priority);
            setDescr(task.descr);
        }
    }, [isEdit])

    const handleSubmit = () => {
        if(!title.trim() && !priority.trim() && !descr.trim()) return onClose();
        if(!title.trim()) {
            Alert.alert('Warning', 'Title field can not be empty!', [
                {
                    text: 'OK'
                }, 
            ], {
                cancelable: true
            })
        }

        else{
            if(isEdit){
                onSubmit(title, priority, descr, Date.now());
            } else {
                onSubmit(title, priority, descr);
                setTitle('');
                setPriority('');
                setDescr('');
            }
            onClose();
        } 
    };

    const closeModal = () => {
        if(!isEdit){
            setTitle('');
            setPriority('');
            setDescr('');
        } 
        onClose();
    }

    return (
        <>
        <StatusBar barStyle='dark-content' backgroundColor={colors.LIGHT}/>
        <Modal visible={visible} animationType='fade'>
            <View style={styles.container}>
                <TextInput 
                    value={title}
                    placeholder='Enter title' 
                    style={[styles.input, styles.title]}
                    onChangeText={(text) => handleOnChangeText(text, 'title')}/>
                <TextInput 
                    value={priority}
                    placeholder='Enter priority level' 
                    style={[styles.input, styles.priority]}
                    onChangeText={(text) => handleOnChangeText(text, 'priority')}/>
                <TextInput 
                    value={descr}
                    multiline
                    placeholder='Enter description' 
                    style={[styles.input, styles.descr]}
                    onChangeText={(text) => handleOnChangeText(text, 'descr')}/>
                <View style={styles.btnContainer}>
                    <RoundIconBtn 
                        size={15} 
                        antIconName='check' 
                        onPress={handleSubmit}/>
                    { title.trim() || priority.trim() || descr.trim() ? <RoundIconBtn 
                        size={15} 
                        style={{marginLeft: 15}}
                        antIconName='close' 
                        onPress={closeModal}/> : null }
                </View>
            </View>
            <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
                <View style={[styles.modalBG, StyleSheet.absoluteFillObject]} />
            </TouchableWithoutFeedback>
        </Modal>
        </>
    );
};

const styles = StyleSheet.create({
    container: {
        paddingHorizontal: 20,
        paddingTop: 15
    },
    input: {
        borderBottomWidth: 2,
        borderBottomColor: colors.PRIMARY,
        fontSize: 20,
        color: colors.DARK
    },
    title: {
        height: 40,
        marginBottom: 15,
        marginTop: 20,
        fontWeight: 'bold'
    },
    priority: {
        height: 40,
        marginBottom: 15,
        marginTop: 20
    },
    descr: {
        height: 100,
    },
    modalBG: {
        flex: 1,
        zIndex: -1
    },
    btnContainer: {
        flexDirection: 'row',
        justifyContent: 'center',
        paddingVertical: 15
    }
})

export default TaskInputModal;
