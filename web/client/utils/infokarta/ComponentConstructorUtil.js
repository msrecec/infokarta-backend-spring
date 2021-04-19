import React from 'react';
import {Button, FormControl, FormGroup, ControlLabel, Carousel, CarouselItem} from 'react-bootstrap';
import parse from 'html-react-parser';

import { beautifyHeader } from "./BeautifyUtil";

// Svrha funkcije: lijep prikaz objekta, moze i feature info
export const displayFeatureInfo = (item, fieldsToExclude = []) => {
    const style = {
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between"
    };

    // const grbUrl = 'https://primosten.hr/wp-content/uploads/2015/12/rsz_789398738793.png';
    // if (property[0].includes('grb')) {
    //     item.properties.grb = `<img src='${grbUrl}' />`;

    let elementList = Object.entries(item).map((property) => {
        if (!fieldsToExclude.includes(property[0]) && property[0].includes('source') && property[1] && typeof property[1] !== "object") {
            return (
                <div style={style}>
                    <span><b>{beautifyHeader(property[0])}</b></span>
                    {parse(property[1])}
                </div>
            );
        } else if (!fieldsToExclude.includes(property[0]) && property[1] && typeof property[1] !== "object") {
            return (
                <div style={style}>
                    <span><b>{beautifyHeader(property[0])}</b></span>
                    <span>{property[1]}</span>
                </div>
            );
        }
        return null;
    });

    // uklanja sve null vrijednosti iz liste
    elementList = elementList.filter(function(el) {
        return el !== null;
    });

    // insertira hr izmedu svakog elementa
    elementList = elementList.flatMap(
        (value, index, array) =>
            array.length - 1 !== index // check for the last item
                ? [value, (<hr style={{margin: "4px"}}/>)]
                : value,
    );

    return elementList;
};

// TODO: rework/reuse za nesto drugo ili delete.
// Nazalost kako react radi, nije moguce vezivat ovako kreiranu formu na lokalni state
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
export const buildDynamicForm = (blueprint, exclude = [], readOnly = [], handleChangeFunction = () => {}) => {
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
                            onChange={(e) => handleChangeFunction(field.value, e)}
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
                            onChange={(e) => handleChangeFunction(field.value, e)}
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

export const buildCarouselFromURLs = (sourceArray, width = 400, height = 220) => {
    return (
        <Carousel>
            {
                sourceArray.map((source) => {
                    return (
                        <CarouselItem>
                            <img width={width} height={height} alt={`${source}`} src={`http://213.191.153.249:8080/static/${source}`} />
                        </CarouselItem>
                    );
                })
            }
        </Carousel>
    );
};
