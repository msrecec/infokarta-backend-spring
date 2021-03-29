import React from 'react';
import {Button, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';
import parse from 'html-react-parser';

import { beautifyHeader } from "./BeautifyUtil";

const style = {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between"
};

// Svrha funkcije: prikaz singularnog feature info-a u bilo kojoj komponenti
export const displayFeatureInfo = (item) => {
    // const grbUrl = 'https://primosten.hr/wp-content/uploads/2015/12/rsz_789398738793.png';
    let elementList = Object.entries(item).map((property) => {
        // if (property[0].includes('grb')) {
        //     item.properties.grb = `<img src='${grbUrl}' />`;
        if (property[0].includes('source') && property[1]) {
            return (
                <div>
                    <div style={style}>
                        <span><b>{beautifyHeader(property[0])}</b></span>
                        {parse(property[1])}
                    </div>
                    <hr />
                </div>
            );
        } else if (property[1]) {
            return (
                <div>
                    <div style={style}>
                        <span><b>{beautifyHeader(property[0])}</b></span>
                        <span>{property[1]}</span>
                    </div>
                    <hr />
                </div>
            );
        }
        return null;

    });
    return elementList;
};

/*
Svrha funkcije: gradi grupe unutar forme. Prima listu objekata:
    - text field:
        label: label koji se pridjeli komponenti
        type: "text"
        value: value na koji se binda u lokalnome stateu
    - select field:
        label: label koji se pridjeli komponenti
        type: "select"
        value: value na koji se binda u lokalnome stateu
        selectValues: lista stringova za odabir. Prvi element liste mora bit prazan string. (ne mora, ali radi izgleda)
    - button:
        label: label koji se pridjeli komponenti
        bsStyle: bootstrap stil
        onClickFunction: onClick funkcija
*/
export const buildDynamicForm = (blueprint, exclude = [], readOnly = []) => {
    let elementList = blueprint.map((field) => {
        if (!exclude.includes(field)) {
            switch (field.type) {
            case "text":
                return (
                    <FormGroup
                        key={field.label}
                        controlId={field.label}
                    >
                        <ControlLabel>{beautifyHeader(field.label)}</ControlLabel>
                        <FormControl
                            type={field.type}
                            value={this.state[field.value]}
                            // onChange={(e) => this.handleChange(field.value, e)}
                            // TODO dodat mogucnost slanja handleChange funkcije u buildDynamicForm
                            readOnly={readOnly.includes(field.value)}/>
                    </FormGroup>
                );
            case "select":
                return (
                    <FormGroup
                        key={field.label}
                        controlId={field.label}
                    >
                        <ControlLabel>{beautifyHeader(field.label)}</ControlLabel>
                        <FormControl
                            componentClass={field.type}
                            placeholder={this.state[field.selectValues[0]]}
                            // onChange={(e) => this.handleChange(field.value, e)}
                            // TODO dodat mogucnost slanja handleChange funkcije u buildDynamicForm
                        >
                            {field.selectValues.map((option) => {
                                return <option value={option}>{option}</option>;
                            })}
                        </FormControl>
                    </FormGroup>
                );
            case "button":
                return (
                    <Button
                        bsStyle={field.bsStyle}
                        onClick={field.onClickfunction}
                    >
                        {field.label}
                    </Button>
                );
            default:
                return null;
            }
        }
        return null;
    });
    return elementList;
};
