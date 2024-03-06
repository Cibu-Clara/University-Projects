import React from "react";
import { View, StyleSheet, Platform } from 'react-native';
import { AntDesign } from '@expo/vector-icons'
import colors from "../resources/colors";

const RoundIconBtn = ({ antIconName, size, color, style, onPress }) => {
    return (
        <View style={styles.outerContainer}>
            <View style={[styles.innerContainer, {...style}]}>
                <AntDesign 
                    name={antIconName} 
                    size={size || 24} 
                    color={color || colors.LIGHT} 
                    style={styles.icon}
                    onPress={onPress}
                />
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    outerContainer: {
        ...Platform.select({
            ios: {
                shadowColor: 'black',
                shadowOffset: { width: 0, height: 2 },
                shadowOpacity: 0.8,
                shadowRadius: 4,
            },
        }),
    },
    innerContainer: {
        backgroundColor: colors.PRIMARY,
        padding: 15,
        ...Platform.select({
            ios: {
                borderRadius: 27,
                overflow: 'hidden'
            },
            android: {
                borderRadius: 50,
                elevation: 5
            },
        }),
    },
})

export default RoundIconBtn;
